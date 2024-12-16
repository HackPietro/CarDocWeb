import { Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-errordialog',
  templateUrl: './errordialog.component.html',
  styleUrls: ['./errordialog.component.css'],
  imports: [MatDialogModule, MatButtonModule],
  standalone: true,
})
export class ErrordialogComponent {}
