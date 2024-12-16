import {Component, OnInit} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import {ServiceService} from "../../Service/service";
import {MatDialog} from "@angular/material/dialog";
import {ErrordialogComponent} from "../errordialog/errordialog.component";
import {SuccessdialogComponent} from "../successdialog/successdialog.component";
@Component({
  selector: 'app-admin-cpl',
  templateUrl: './admin-cpl.component.html',
  styleUrls: ['./admin-cpl.component.css']
})
export class AdminCplComponent implements OnInit{
  public RimuoviUserForm: FormGroup = new FormGroup({});
  public TypeUserForm: FormGroup = new FormGroup({});

  showSuccessMessage: boolean = false;
  showErrorMessage: boolean = false;


  constructor(private service: ServiceService, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.RimuoviUserForm = new FormGroup({
      cf: new FormControl()
    })
    this.TypeUserForm =  new FormGroup({
      cf: new FormControl(),
      tipologia: new FormControl()
    })
  }

  onRemoveUserFormSubmit(){
    let cf = this.RimuoviUserForm.value.cf;
    if (cf == null || cf == "null"){
      return;
    }
    this.service.getUtente(cf).subscribe({
      next: (utente) => {
        if (utente != null){
          this.showSuccessMessage = true;
          this.showErrorMessage = false;
          this.service.removeUtente(cf).subscribe();
          this.RimuoviUserForm.reset();
        }
      },
      error:() =>{
        this.showSuccessMessage = false;
        this.showErrorMessage = true;
      }
    })
  }

  setUserType(){
    let cf = this.TypeUserForm.value.cf;
    let tipologia = this.TypeUserForm.value.tipologia.toLowerCase();
    this.service.getUtente(cf).subscribe({
      next: (utente)=>{
        if (utente != null && (tipologia == "admin" || tipologia == "utente")){
          this.service.setUserType({
            nome: utente.nome,
            cognome: utente.cognome,
            cf: utente.cf,
            telefono: utente.telefono,
            email: utente.email,
            password: utente.password,
            tipologia: tipologia,
          }).subscribe()
          this.dialog.open(SuccessdialogComponent);
          this.TypeUserForm.reset();
        }else{
          this.dialog.open(ErrordialogComponent);
        }
      }
    })
  }


}
