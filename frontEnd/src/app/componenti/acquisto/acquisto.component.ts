import { Component } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatDialog} from "@angular/material/dialog";
import {ErrordialogComponent} from "../errordialog/errordialog.component";
import {SuccessdialogComponent} from "../successdialog/successdialog.component";
import {ServiceService} from "../../Service/service";
import {forkJoin, Observable} from "rxjs";
import {Carrello} from "../../Model/Carrello";


@Component({
  selector: 'app-acquisto',
  templateUrl: './acquisto.component.html',
  styleUrl: './acquisto.component.css'
})
export class AcquistoComponent {
  duration: any =  1000;
  utente: any = localStorage.getItem("cf");
  lastNumberOrder: number = 0;
  cart: Carrello[] = [];


  /* ordini */
  totalQuantity: number = 0;
  totalPrice: number = 0;


  constructor(private _formBuilder: FormBuilder, public dialog: MatDialog, private service: ServiceService) {}
  nameFormGroup: FormGroup = this._formBuilder.group({name: ['']});
  surnameFormGroup: FormGroup = this._formBuilder.group({surname: ['']});
  countryFormGroup: FormGroup = this._formBuilder.group({country: ['']});
  provinceFormGroup: FormGroup = this._formBuilder.group({province: ['']});
  capFormGroup: FormGroup = this._formBuilder.group({cap: ['']});
  addressFormGroup: FormGroup = this._formBuilder.group({address: ['']});
  paymentFormGroup: FormGroup = this._formBuilder.group({
    cardNumber: [''],
    expiryDate: [''],
    cvc: ['']
  });


  isFormValid() {
    if(
      this.isStepValid(this.nameFormGroup, 'name') &&
      this.isStepValid(this.surnameFormGroup, 'surname') &&
      this.isStepValid(this.countryFormGroup, 'country') &&
      this.isStepValid(this.provinceFormGroup, 'province') &&
      this.isStepValid(this.capFormGroup, 'cap') &&
      this.isStepValid(this.addressFormGroup, 'address') &&
      this.isStepValid(this.paymentFormGroup, 'cardNumber') &&
      this.isStepValid(this.paymentFormGroup, 'expiryDate') &&
      this.isStepValid(this.paymentFormGroup, 'cvc')
    ){
      this.dialog.open(SuccessdialogComponent).afterClosed().subscribe(() => {
        this.addProductOnOrder();
      });
    }else{
      this.dialog.open(ErrordialogComponent);
    }
  }

  isStepValid(step: FormGroup, controlName: string): boolean {
    let control = step.get(controlName);
    if (control && control.value){
      return this.validateField(controlName, control.value);
    }
    return false;
  }

  validateField(fieldName: string, value: any): boolean {
    switch (fieldName) {
      case 'name' || 'surname' || 'address' || 'country' || 'province':
        return /[a-zA-Z]/.test(value);
      case 'cap':
        return /^\d{5}$/.test(value);
      case 'cardNumber':
        value = value.replace(/\s/g, '');
        return /^\d{16}$/.test(value);
      case 'expiryDate':
        const currentDate = new Date();
        const currentYear = currentDate.getFullYear();

        const monthYear = value.split('/');
        const month = Number(monthYear[0]);
        const year = Number(monthYear[1]);

        return month >= 1 && month <= 12 && year >= currentYear;
      case 'cvc':
        return /^\d{3}$/.test(value);
      default:
        return true;
    }
  }

  annulla(){
    window.location.reload();
  }





  addProductOnOrder() {
    this.service.findLastNumberOrder(this.utente).subscribe({
      next: (lastNumberOrder) => {
        this.lastNumberOrder = lastNumberOrder;

        this.service.getCart(this.utente).subscribe({
          next: (cart) => {
            let observables: Observable<any>[] = [];
            this.cart = cart;
            this.cart.forEach(cartItem => {
              this.totalPrice += cartItem.prezzo;
              this.totalQuantity++;

              observables.push(this.service.insertOrderDetail({
                cf: this.utente,
                idProdotto: cartItem.idProdotto,
                numeroOrdine: this.lastNumberOrder,
                quantita: cartItem.quantity,
                prezzo: cartItem.prezzo,
              }));
            });

            // Combina tutti gli observable in modo da eseguire il codice successivo solo quando tutti sono completati
            forkJoin(observables).subscribe(() => {
              this.service.insertOrder({
                numeroOrdine: this.lastNumberOrder,
                numeroVenduti: this.totalQuantity,
                prezzoTotale: this.totalPrice,
                cf: this.utente,
              }).subscribe(() => {
                this.service.removeAllForCart(this.utente).subscribe();
                window.location.reload();
              });
            });
          }
        });
      }
    });
  }

}
