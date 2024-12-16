import {latLng, tileLayer, Map, marker} from 'leaflet';

export function initMap(addressComponents: any) {
  const apiUrl = `https://nominatim.openstreetmap.org/search?street=${addressComponents.street}&city=${addressComponents.city}&county=${addressComponents.county}&postalcode=${addressComponents.postalcode}&zoomOffset=1&format=json`;

  const mapOptions = {
    layers: [
      tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors'
      })
    ],
    center: latLng(0, 0)
  };

  const map = new Map('leafletMap', mapOptions);

  fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
      if (data.length > 0) {
        const {lat, lon} = data[0];
        map.setView(latLng(lat, lon), 17);

        const customMarker = marker([lat, lon])
          .addTo(map)
          .bindPopup('CarDoc Store.<br> Cosenza, 87036.')
          .openPopup();
      }
    });
}
