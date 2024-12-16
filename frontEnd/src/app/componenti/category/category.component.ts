import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {BreakpointObserver, BreakpointState} from "@angular/cdk/layout";


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit{
  formRicerca: FormGroup = new FormGroup({});
  smallDevice: boolean = false;

  constructor(private breakpointObserver: BreakpointObserver) {
    this.breakpointObserver.observe(["(max-width: 600px)"]).subscribe((result: BreakpointState) => {
      if (result.matches) {
        this.smallDevice = true;
      } else {
        this.smallDevice = false;
      }
    });
  }

  ngOnInit(): void {
    this.formRicerca = new FormGroup({
      searchWord: new FormControl()
    });
  }

  selectedCategoryChange(category: string){
    localStorage.setItem("categoria", category);
    window.open('http://localhost:4200/home','_self');
  }

  onSearch(){
    localStorage.setItem("searchedWord", this.formRicerca.value.searchWord);
  }
}
