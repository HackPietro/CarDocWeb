import {Component, OnInit} from '@angular/core';
import {ServiceService} from "../../Service/service";
import {BreakpointObserver, BreakpointState} from "@angular/cdk/layout";
import {Prodotto} from "../../Model/Prodotto";
import {FormControl, FormGroup} from "@angular/forms";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit{

  prodotti: Prodotto[] = [];
  images: { [key: string]: string } = {};
  smallDevice: boolean = false;
  selectedCategory: any = "";
  searchedWord: any = "";
  noProductFound: boolean = false;
  formFiltri : FormGroup = new FormGroup({});
  tipoSortValue : string = "";



  ngOnInit(): void {
    this.setCategoryProduct();
    this.getSearchedWord();
    this.getAllImage();

    if (this.selectedCategory != null) {
      this.service.getCategoryProduct(this.selectedCategory).subscribe({
        next: (prodotti) => {
          this.prodotti = prodotti;
        },
      });
    } else if (this.searchedWord != null) {
      this.service.getSearchedProduct(this.searchedWord).subscribe({
        next: (prodotti) => {
          this.prodotti = prodotti;
          if (this.prodotti.length == 0){
            this.noProductFound = true;
          }
        },
      });
    } else {
      this.service.getProdotti().subscribe({
        next: (prodotti) => {
          this.prodotti = prodotti;
        },
      });
    }
    localStorage.removeItem("categoria");
    localStorage.removeItem("searchedWord");

    this.formFiltri = new FormGroup({
      sort: new FormControl()
    })

    this.tipoSortValue = "";
  }


  constructor(private breakpointObserver: BreakpointObserver, private service: ServiceService) {
    // detect screen size changes
    this.breakpointObserver.observe(["(max-width: 600px)"]).subscribe((result: BreakpointState) => {
      if (result.matches) {
        this.smallDevice = true;
      } else {
        this.smallDevice = false;
      }
    });
  }



  setCategoryProduct(){
      this.selectedCategory = localStorage.getItem("categoria");
  }

  getSearchedWord(){
    this.searchedWord = localStorage.getItem("searchedWord");
  }


  tipoSortValueChange(event: any) {
    this.tipoSortValue = event.target.value;
    this.sort();
  }

  sort() {
    if (this.tipoSortValue === "crescente") {
      this.prodotti.sort((a, b) => a.prezzo - b.prezzo);
    } else if (this.tipoSortValue === "decrescente") {
      this.prodotti.sort((a, b) => b.prezzo - a.prezzo);
    } else if (this.tipoSortValue === "A-Z") {
      this.prodotti.sort((a, b) => a.nome.localeCompare(b.nome));
    } else if (this.tipoSortValue === "Z-A") {
      this.prodotti.sort((a, b) => b.nome.localeCompare(a.nome));
    } else if (this.tipoSortValue === "maxVenduti") {
      this.prodotti.sort((a, b) => b.numeroVenduti - a.numeroVenduti);
    } else if (this.tipoSortValue === "minVenduti") {
      this.prodotti.sort((a, b) => a.numeroVenduti - b.numeroVenduti);
    }
  }


  getAllImage() {
    if (Object.keys(this.images).length != 0) {
      return;
    }
    this.service.getProdotti().subscribe({
      next: (prodotti) => {
        prodotti.forEach(prod => {
          this.service.findImageByProductID(prod.id).subscribe({
            next: (img) => {
              if (img != null) {
                const base64String = img.img;
                this.images[prod.id] = base64String;
              }
            }
          });
        });
      }
    });
  }

}
