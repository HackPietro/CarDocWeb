import {Component, OnInit} from '@angular/core';
import {DettagliOrdine} from "../../Model/DettagliOrdine";
import {ServiceService} from "../../Service/service";
import {MatDialog} from "@angular/material/dialog";
import {Utente} from "../../Model/Utente";


@Component({
  selector: 'app-dettagli-ordine',
  templateUrl: './dettagli-ordine.component.html',
  styleUrl: './dettagli-ordine.component.css'
})
export class DettagliOrdineComponent implements OnInit{
  dettagliOrdine: DettagliOrdine[] = [];
  utente: any = localStorage.getItem("cf");
  images: { [key: string]: string } = {};
  infoUtente: Utente | undefined;
  dataOrdine: any = localStorage.getItem("dataOrdine");



  constructor(private service: ServiceService, public dialog: MatDialog) {}


  ngOnInit() {
    this.loadDetailOrder()
    this.findDetailUtenteByCf()
  }

  loadDetailOrder(){
    this.service.getDetailOrderByNumber({
      cf: localStorage.getItem("cf"),
      numeroOrdine: localStorage.getItem("ordineSelezionato"),
    }).subscribe({
      next: (dettagliOrdine) => {
        this.dettagliOrdine = dettagliOrdine;
        dettagliOrdine.forEach(dettagli => {
          this.service.findImageByProductID(dettagli.idProdotto).subscribe({
            next: (img) => {
              if (img != null) {
                const base64String = img.img;
                this.images[dettagli.idProdotto] = base64String;
              }
            }
          });
        });
      }
    })
  }

  findDetailUtenteByCf(){
    this.service.findDetailUtenteByCf(this.utente).subscribe({
      next:(utente) =>{
        this.infoUtente = utente;
      }
    })
  }

}
