// if(document.readyState == 'loading'){
//   document.addEventListener('DOMContentLoaded',initMap)
// }else{
//    initMap();
// }
//google.maps.event.addDomListener(window, "load", init);
var map;
var infoWindow;
var geocoder;
function initMap() {
  infoWindow = new google.maps.InfoWindow;
  geocoder = new google.maps.Geocoder();
  map = new google.maps.Map(document.getElementById('googlemap'), {
    center: { lat: 33.50393571438486, lng: -112.04478456037113 },
    zoom: 8
  });
  // geocoder.geocode({'address': city}, function(results, status) {
  //     if (status === 'OK') {
  //         map.setCenter(results[0].geometry.location);
  //     } else {
  //         alert('Geocode was not successful for the following reason: ' + status);
  //     }
  // });
  addMapMarkers(resultLocations);
}

function addMapMarkers(addresses){
  console.log("addresses.length " + addresses.length);
  for(let i=0; i<addresses.length; i++){
    console.log("id: " + addresses[i].id + " lat: " + addresses[i].latitude + " lng: " + addresses[i].longitude);
    var spotDetails =
        '<div class="infoWindow">'+
        '      <p>'+ addresses[i].address1 + '</p>'+
        '      <div class="viewGMaps"><a target="_blank" href="https://www.google.com/maps/search/?api=1&query='+ addresses[i].latitude + ','+ addresses[i].longitude + '"' +'>View on Google Maps</a></div>'+
        '    </div>';

    var marker = new google.maps.Marker({
      id: addresses[i].id,
      map: map,
      position: {lat: addresses[i].latitude, lng: addresses[i].longitude},
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