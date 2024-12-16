import {Injectable} from '@angular/core';
import {ServiceService} from "../Service/service";
import {ActivatedRoute} from "@angular/router";
import {Utente} from "../Model/Utente";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  sessionId: string | null | undefined;
  utenteCorrente: any;
  isLoggedIn: Boolean = false;
  selectedProduct: string = "";

  constructor(private service: ServiceService, private route: ActivatedRoute) {
    this.checkLogin();
  }


  checkLogin(): boolean {
    /*
     * Ritorna true se l'utente è già loggato e le informazioni sono già caricate
    */
    if (this.utenteCorrente != null) return true;
    this.sessionId = this.route.snapshot.queryParams['sessionId'];
    this.service.getUserDetails(this.sessionId).subscribe({
      next: (data) => {
        this.utenteCorrente = new Utente();
        this.utenteCorrente = data;
        this.isLoggedIn = true;
        if (typeof window !== 'undefined') {
          localStorage.setItem("nome", this.utenteCorrente.nome);
          localStorage.setItem("cognome", this.utenteCorrente.cognome);
          localStorage.setItem("telefono", this.utenteCorrente.telefono);
          localStorage.setItem("tipologia", this.utenteCorrente.tipologia);
          localStorage.setItem("email", this.utenteCorrente.email);
          localStorage.setItem("password", this.utenteCorrente.password);
          localStorage.setItem("cf", this.utenteCorrente.cf);
        }
        return true;

      },
      error: () => {
        const userFromLS = localStorage.getItem("cf");
        if (userFromLS == null || userFromLS == undefined) {
          this.isLoggedIn = false;
          console.log("[!] SessionID not valid, and user not found in local storage!");
          return false;
        }
        this.isLoggedIn = true;
        this.utenteCorrente = new Utente();
        this.utenteCorrente.nome = localStorage.getItem("nome");
        this.utenteCorrente.cognome = localStorage.getItem("cognome");
        this.utenteCorrente.telefono = localStorage.getItem("telefono");
        this.utenteCorrente.tipologia = localStorage.getItem("tipologia");
        this.utenteCorrente.email = localStorage.getItem("email");
        this.utenteCorrente.password = localStorage.getItem("password");
        this.utenteCorrente.cf = localStorage.getItem("cf");
        return true;
      },
    });
    return false;
  }

  isAdmin(): boolean {
    return this.utenteCorrente.tipologia == "admin";
  }

  logout(): void {
    this.utenteCorrente = null;
    this.isLoggedIn = false;
    this.sessionId = null;
    localStorage.removeItem("nome");
    localStorage.removeItem("cognome");
    localStorage.removeItem("telefono");
    localStorage.removeItem("email");
    localStorage.removeItem("password");
    localStorage.removeItem("cf");
    localStorage.removeItem("tipologia");
  }




}
