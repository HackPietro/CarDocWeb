import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './componenti/home/home.component';
import {AuthenticationComponent} from "./componenti/authentication/authentication.component";
import { ProfiloComponent } from './componenti/profilo/profilo.component';
import {AdminCplComponent} from "./componenti/admin_panel/admin-cpl.component";
import { ProdottoComponent } from './componenti/prodotto/prodotto.component';
import { CategoryComponent } from './componenti/category/category.component';
import { AddReviewComponent } from './componenti/add-review/add-review.component';
import {CartComponent} from './componenti/cart/cart.component';
import { PasswordRecoveryComponent } from './componenti/password-recovery/password-recovery.component';
import { DettagliOrdineComponent } from './componenti/dettagli-ordine/dettagli-ordine.component';
import { WishlistComponent } from './componenti/wishlist/wishlist.component';
import { AggiungiProdottoComponent } from './componenti/aggiungi-prodotto/aggiungi-prodotto.component';
import { ErrorcartComponent } from './componenti/errorcart/errorcart.component';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatMenuModule } from '@angular/material/menu';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import { MatCheckboxModule } from '@angular/material/checkbox'
import {AppRoutingModule} from "./app-routing.module";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { FooterComponent } from './componenti/footer/footer.component';
import { AcquistoComponent } from './componenti/acquisto/acquisto.component';
import {MatStepperModule} from "@angular/material/stepper";



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AuthenticationComponent,
    ProfiloComponent,
    AdminCplComponent,
    ProdottoComponent,
    CategoryComponent,
    AddReviewComponent,
    FooterComponent,
    AcquistoComponent,
    CartComponent,
    DettagliOrdineComponent,
    PasswordRecoveryComponent,
    WishlistComponent,
    AggiungiProdottoComponent,
    ErrorcartComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,

    FormsModule,
    ReactiveFormsModule,

    MatFormFieldModule,
    MatButtonModule,
    MatSidenavModule,
    MatListModule,
    MatToolbarModule,
    MatIconModule,
    MatTableModule,
    MatCardModule,
    MatInputModule,
    MatMenuModule,
    MatGridListModule,
    MatSelectModule,
    MatDialogModule,
    FontAwesomeModule,
    MatCheckboxModule,
    MatStepperModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
