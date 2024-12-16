import {Component, OnInit} from '@angular/core';
import { faFacebook, faWhatsapp } from '@fortawesome/free-brands-svg-icons';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import {ActivatedRoute} from "@angular/router";
import {ServiceService} from "../../Service/service";
import {Prodotto} from "../../Model/Prodotto";
import {Recensione} from "../../Model/Recensione";
import {AuthService} from "../../auth/auth.service";
import {ErrordialogComponent} from "../errordialog/errordialog.component";
import {MatDialog} from "@angular/material/dialog";
import {AddReviewComponent} from "../add-review/add-review.component";
import {BreakpointObserver, BreakpointState} from "@angular/cdk/layout";

@Component({
  selector: 'app-prodotto',
  templateUrl: './prodotto.component.html',
  styleUrl: './prodotto.component.css'
})
export class ProdottoComponent implements OnInit{

  smallDevice: boolean = false;

  stringID: string = "";
  nome: string = "";
  descrizione: string = "";
  prezzo: number = 0;
  venditore: string = "";
  categoria: string = "";
  imagesSingleProduct: string = "";

  relatedProductimages: { [key: string]: string } = {};

  prodottiCorrelati: Prodotto[] = [];
  recensioni: Recensione[] = []




  menuOpen = false;
  faFacebook = faFacebook;
  faWhatsapp = faWhatsapp;
  faEnvelope = faEnvelope;


  constructor(private breakpointObserver: BreakpointObserver, private route: ActivatedRoute, private service: ServiceService, public auth: AuthService, public dialog: MatDialog) {
    this.breakpointObserver.observe(["(max-width: 600px)"]).subscribe((result: BreakpointState) => {
      if (result.matches) {
        this.smallDevice = true;
      } else {
        this.smallDevice = false;
      }
    });
  }

  ngOnInit(): void {

    document.addEventListener('DOMContentLoaded', function () {
      const downButton = document.querySelector('.down') as HTMLElement;
      const upButton = document.querySelector('.up') as HTMLElement;
      const inputField = document.querySelector('#quantityValue') as HTMLInputElement;

      // Aggiungi gestori di eventi per cliccare su "+" e "-"
      downButton.addEventListener('click', () => {
        const value = parseInt(inputField.value);
        if (value > 1) {
          inputField.value = (value - 1).toString();
        }
      });

      upButton.addEventListener('click', () => {
        const value = parseInt(inputField.value);
        inputField.value = (value + 1).toString();
      });
    });
    this.getProductInfo();
  }


  getProductInfo(){
    this.stringID += this.route.snapshot.paramMap.get("id");
    this.service.getProduct(this.stringID).subscribe({
      next: (prodotto) =>{
        this.nome = prodotto.nome;
        this.descrizione = prodotto.descrizione;
        this.prezzo = prodotto.prezzo;
        this.venditore = prodotto.venditore;
        this.categoria = prodotto.categoria;
        this.getRelatedProducts();
        this.service.findImageByProductID(prodotto.id).subscribe({
          next: (img) => {
            if (img != null) {
              const base64String = img.img;
              this.imagesSingleProduct = base64String;
            }
          }
        });

        this.service.getRecensioniByProdottoID(prodotto.id).subscribe({
          next: (recensioni) => {
            if(recensioni.length > 0) {
              this.recensioni = recensioni;
              this.recensioni.forEach(recensione => {
                this.service.getUtente(recensione.utente).subscribe({
                  next: (utente) => {
                    recensione.utente = utente.email;
                  }
                })
              })
            }
          }
        })
      }
    });


  }

  getRelatedProducts(){
    this.service.getCategoryProduct(this.categoria).subscribe({
      next: (prodotti) =>{
        prodotti = prodotti.filter(prod => prod.id !== this.stringID)
        this.prodottiCorrelati = prodotti;
        this.prodottiCorrelati.forEach(prod =>{
          this.service.findImageByProductID(prod.id).subscribe({
            next: (img) => {
              if (img != null) {
                const base64String = img.img;
                this.relatedProductimages[prod.id] = base64String;
              }
            }
          });
        })
    }
    })
  }

  deleteRecensione(id: number){
    this.service.deleteRecensione(id).subscribe({
      next: () => window.location.reload(),
      error: () => this.dialog.open(ErrordialogComponent),
    })
  }

  checkIfSameReviewOwner(recensione: Recensione): Boolean {
    return recensione.utente == localStorage.getItem('email');
  }

  shareAction(){
    this.menuOpen = !this.menuOpen;
  }

  shareTo(social: string): void {
    const shareUrl = `localhost:4200/prodotto/${this.stringID}`;
    const shareText = `Guarda questo prodotto: ${this.nome}\nDescrizione: ${this.descrizione}\nPrezzo: ${this.prezzo}€`;

    switch (social) {
      case 'facebook':
        const facebookUrl = `https://www.facebook.com/sharer/sharer.php?quote=${encodeURIComponent(shareText)}`;
        window.open(facebookUrl, '_blank');
        break;

      case 'whatsapp':
        const whatsappUrl = `https://api.whatsapp.com/send?text=${encodeURIComponent(shareText + '\n' + shareUrl)}`;
        window.open(whatsappUrl, '_blank');
        break;

      case 'mail':
        const subject = encodeURIComponent(`Check out this product: ${this.nome}`);
        const body = encodeURIComponent(`${this.descrizione}\nPrice: ${this.prezzo}€\n\n${shareUrl}`);
        const mailtoUrl = `mailto:?subject=${subject}&body=${body}`;
        window.location.href = mailtoUrl;
        break;

      default:
        break;
    }
  }

  addReview(): void {
    this.auth.selectedProduct = this.stringID;
    this.dialog.open(AddReviewComponent).afterClosed().subscribe({
      next: () => window.location.reload()
    })
  }

  addToCart() {
    const quantityInput = document.getElementById('quantityValue') as HTMLInputElement;
    const quantityValue = parseInt(quantityInput.value, 10);
    let utente = localStorage.getItem("cf");
    if (utente == null){
      utente = "null";
    }

    this.service.addToCart({
      cf: utente,
      idProdotto: this.stringID,
      quantity: quantityValue,
      prezzo: quantityValue * this.prezzo,
    }).subscribe({
      next: () => {
        window.location.reload();
      }
    });
  }


  addToWishlist() {
    let utente = localStorage.getItem("cf");
    if (utente == null){
      utente = "null";
    }

    this.service.addToWishlist({
      cf: utente,
      idProdotto: this.stringID,
      prezzo: this.prezzo,
    }).subscribe({
      next: () => {
        window.location.reload();
      }
    });
  }


}
