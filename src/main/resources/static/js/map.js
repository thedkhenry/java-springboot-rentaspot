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
        '<div class="infoWindow text-center">'+
        '      <h6><b><a href="/' + locations[i].id + '" class="text-black">'+ locations[i].title + '</a></b></h6>'+
        '      <div class="my-2">'+ locations[i].address.fullAddress + '</div>'+
        '      <div class="row justify-content-between h6">'+
        '            <div class="col">$'+ locations[i].price + '/day </b> • '+ locations[i].totalOccupancy +' Cars</div>'+
        '            <div class="col">⭐'+ locations[i].weightedAverage +'</div>'+
        '      </div>'+
        '      <div class="viewGMaps"><a target="_blank" href="https://www.google.com/maps/search/?api=1&query='+ locations[i].address.latitude + ','+ locations[i].address.longitude + '"' +'>View on Google Maps</a></div>'+
        '    </div>';

    var marker = new google.maps.Marker({
      id: locations[i].address.id,
      map: map,
      position: {lat: locations[i].address.latitude, lng: locations[i].address.longitude},
      info: spotDetails
    });

    google.maps.event.addListener(marker, 'click', function() {
      infoWindow.setContent(this.info);
      infoWindow.open(map,this);
      console.log("id: " + this.id)
      console.log(this.position.toJSON());
    });
  }
}