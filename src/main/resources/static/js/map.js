var map;
var infoWindow;

function initMap() {
  infoWindow = new google.maps.InfoWindow;
  geocoder = new google.maps.Geocoder();
  map = new google.maps.Map(document.getElementById('googlemap'), {
    center: { lat: 33.50393571438486, lng: -112.04478456037113 },
    zoom: 8
  });
  addMapMarkers(resultLocations);
}

function addMapMarkers(locations){
  console.log("locations.length " + locations.length);
  for(let i=0; i<locations.length; i++){
    console.log("id: " + locations[i].address.id + " lat: " + locations[i].address.latitude + " lng: " + locations[i].address.longitude);
    var spotDetails =
        '<div class="infoWindow">'+
        '      <p><b>'+ locations[i].title + '</b></p>'+
        '      <p><b>$'+ locations[i].price + '</b></p>'+
        '      <p>'+ locations[i].address.fullAddress + '</p>'+
        '      <div class="viewGMaps"><a target="_blank" href="https://www.google.com/maps/search/?api=1&query='+ locations[i].address.latitude + ','+ locations[i].address.longitude + '"' +'>View on Google Maps</a></div>'+
        '    </div>';

    var marker = new google.maps.Marker({
      id: locations[i].address.id,
      map: map,
      position: {lat: locations[i].address.latitude, lng: locations[i].address.longitude},
      info: spotDetails
    });

    // markers.push(marker);

    google.maps.event.addListener(marker, 'click', function() {
      var markerId = this.id;
      infoWindow.setContent(this.info);
      infoWindow.open(map,this);
      console.log("id: " + this.id + " SpotPos: ")
      console.log(this.position.toJSON());
    });
  }
}