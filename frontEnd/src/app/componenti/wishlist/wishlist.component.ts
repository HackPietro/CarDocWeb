import { Component, OnInit } from '@angular/core';
import { Prodotto } from "../../Model/Prodotto";
import { ActivatedRoute } from "@angular/router";
import { ServiceService } from "../../Service/service";
import { MatDialog } from "@angular/material/dialog";


@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})

export class WishlistComponent implements OnInit {
  utente: any = localStorage.getItem("cf");
  wishlist: Prodotto[] = [];
  images: { [key: string]: string } = {};

  constructor(private route: ActivatedRoute, private service: ServiceService, public dialog: MatDialog) {}

  ngOnInit() {
    this.loadWishlistData();
  }

  loadWishlistData() {
    this.service.getWishlistProduct(this.utente).subscribe({
      next: (wishlist) => {
        this.wishlist = wishlist;
        this.wishlist.forEach((prod: Prodotto) => {
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


  removeItem(id_prodotto: string) {
    this.service.removeItemForWishlist(this.utente, id_prodotto).subscribe({
      next: () => window.location.reload()
    });
  }

  removeAll(cf: string) {
    this.service.removeAllForWishlist(cf).subscribe({
      next: () => window.location.reload()
    });
  }

  protected readonly length = length;
}
