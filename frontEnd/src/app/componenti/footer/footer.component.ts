import { Component, OnInit, AfterViewInit, PLATFORM_ID, Inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit, AfterViewInit {
  address: any;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {
    this.address = {
      street: 'V.le delle Scienze',
      city: 'Quattromiglia',
      county: 'CS',
      postalcode: 87036
    };
    this.address.street = this.address.street.split(" ").join("+");
  }

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      import('./leaflet-map').then((mapModule) => {
        mapModule.initMap(this.address);
      });
    }
  }
}
