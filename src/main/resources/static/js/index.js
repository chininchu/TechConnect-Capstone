
function upcomingEvents(){
    let html = "";
    fetch(`https://www.techconnect.expert/events/closestEvents`)
        .then( response => { response.json()
            .then(events => {
                for (let i = 0; i < 3; i++) {
                    console.log(events[i].title)
                    html += `<div class="col-lg-3">`
                    html += `<div class="card">`
                    html += `<div class="card-body">`
                    html += `<h5 class="card-title" style="color: #03A9F4">${events[i].title}</h5>`
                    html += `<p class="card-text">${events[i].description}</p>`
                    html += `<a href="/event/${events[i].id}/reviews" class="btn text-light" id="read">Read More</a>`
                    html += `</div>`
                    html += `</div>`
                    html += `</div>`
                    $(".card-row").html(html);
                }
            })
        })

}


upcomingEvents()
