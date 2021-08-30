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

function updateBooking(action, id, loc, name,cars, start, end){
    var title = document.querySelector('#modalTitle')
    var message = document.querySelector('#modalMessage')
    var link = document.querySelector('#actionLink')
    if(action === 'confirm'){
        link.classList.remove("btn-danger");
        link.innerHTML = 'Confirm it';
        link.classList.add("btn-success");
    }else if (action === 'cancel'){
        link.classList.remove("btn-success");
        link.innerHTML = 'Cancel it';
        link.classList.add("btn-danger");
    }
    title.innerHTML = 'Do you want to ' + action + ' <b>' + name + '\'s</b> reservation?';
    message.innerHTML = cars + ' Cars for ' + start + ' - ' + end;
    link.setAttribute("href","/reservations/"+action+"/" +loc+"/" + id);
}

function updateListing(){
    //
}