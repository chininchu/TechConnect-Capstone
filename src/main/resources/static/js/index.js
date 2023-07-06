
function upcomingEvents(){
    let html = "";
    fetch(`http://localhost:8080/events/closestEvents`)
        .then( response => { response.json()
            .then(events => {
                for (let i = 0; i < events.length; i++) {
                    let html = "";
                    console.log(events[i].title)
                    html += `<div class="col-lg-3">`
                    html += `<div class="card">`
                    // html += `<img src="card1.jpg" class="card-img-top" alt="Card 1">`
                    html += `<div class="card-body">`
                    html += `<h5 class="card-title">${events[i].title}</h5>`
                    html += `<p class="card-text">${events[i].description}</p>`
                    html += `<a href="#" class="btn btn-light">Read More</a>`
                    html += `</div>`
                    html += `</div>`
                    html += `</div>`
                    $(".card-row").html(html);
                }
            })
        })

}


upcomingEvents()
