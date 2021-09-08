//CreateListing
function showModal(){
    document.querySelector('#modalLocationTitle').innerHTML = document.querySelector('#title').value;
}

function updateStars() {
    var rangeValue = document.querySelector("#rating").value;
    var stars = "";
    for(let i = 0; i < rangeValue; i++){
        stars += "⭐";
    }
    document.querySelector("#ratingText").innerHTML = stars;
}

//Reservations page Cancel button
function cancelMyReservation(btn){
    var id = btn.getAttribute("data-id");
    var locationTitle = btn.getAttribute("data-title");
    var title = document.querySelector('#modalTitle')
    var message = document.querySelector('#modalMessage')
    var link = document.querySelector('#actionLink')
    link.innerHTML = 'Cancel it';
    link.classList.add("btn-danger");
    title.innerHTML = 'Do you want to cancel your reservation?';
    message.innerHTML = locationTitle;
    link.setAttribute("href","/reservations/cancel/" + id);
}

