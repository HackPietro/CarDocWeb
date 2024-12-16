import { Component } from '@angular/core';
import {ServiceService} from "../../Service/service";



@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrl: './password-recovery.component.css'
})
export class PasswordRecoveryComponent {

  email: string = '';
  showSuccessMessage: boolean = false;
  showErrorMessage: boolean = false;

  constructor(private service: ServiceService) {
  }

  submitRecoveryRequest() {
    this.service.getUtente(this.email).subscribe({
      next: (utente) => {
        if (utente != null) {
          this.showSuccessMessage = true;
          this.showErrorMessage = false;
          this.email = utente.email;
          this.service.sendEmail({
            cf: utente.cf,
            nome: utente.nome,
            cognome: utente.cognome,
            email: utente.email,
            tipologia: utente.tipologia,
            telefono: utente.telefono,
            password: utente.password,
          }).subscribe();
        }
      },
      error: (error) => {
        this.showSuccessMessage = false;
        this.showErrorMessage = true;
      }
    });
  }







}
