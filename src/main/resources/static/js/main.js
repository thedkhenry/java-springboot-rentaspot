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

