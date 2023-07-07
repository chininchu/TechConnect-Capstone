
function upcomingEvents(){
    let html = "";
    fetch(`http://localhost:8080/events/closestEvents`)
        .then( response => { response.json()
            .then(events => {
                for (let i = 0; i < 4; i++) {
                    console.log(events[i].title)
                    html += `<div class="col-lg-3">`
                    html += `<div class="card">`
                    html += `<div class="card-body">`
                    html += `<h5 class="card-title">${events[i].title}</h5>`
                    html += `<p class="card-text">${events[i].description}</p>`
                    html += `<a href="/event/${events[i].id}/reviews" class="btn btn-light">Read More</a>`
                    html += `</div>`
                    html += `</div>`
                    html += `</div>`
                    $(".card-row").html(html);
                }
            })
        })

}


upcomingEvents()
