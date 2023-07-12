document.addEventListener('DOMContentLoaded', function () {
// -----------PAGE MAP--------------
    mapboxgl.accessToken = MAPBOXAP_TOK;
    const coordinates = document.getElementById('coordinates');
    let map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/navigation-day-v1',
        center: [-95.7129, 37.0902],
        marker: [-95.7129, 37.0902],
        zoom: 3,
    });
    let marker = new mapboxgl.Marker({})
        .setLngLat([0,0])
        .addTo(map);

// ---------------SHOWS ALL EVENTS ON MAP & CALENDAR-----------
    function allEvents() {
        let html= ""
        fetch("/events/allEvents")
            .then(response => {
                response.json()
                    .then(events => {
                        events.forEach(event => {
                            console.log(event.location);
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(3)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                html += `<div class="card">`
                                html += `<div class="card-body">`
                                html += `<a href="/event/${event.id}/reviews"><h5>${event.title}</h5></a>`
                                html += `<p>${event.description}</p>`
                                html += `<p>${event.location}</p>`
                                html += `</div>`
                                html += `</div>`
                                $('.events').html(html)
                            })
                        })
                    })
            })
    }
allEvents()
    function clearEventsDiv() {
        var eventsDiv = document.querySelector('.events');
        eventsDiv.innerHTML = '';
        console.log(eventsDiv);
    }

// --------------FIND EVENT BY LOCATION----------
    function getEventByLocation() {
        clearEventsDiv()
        let submitBtn = document.getElementById("subBtn")
        submitBtn.addEventListener("click", function (event) {
            event.preventDefault();
            let userInput = document.getElementById("location").value
            let html= ""
            map.remove()
            map = new mapboxgl.Map({
                container: 'map',
                style: 'mapbox://styles/mapbox/navigation-day-v1',
                center: [-95.7129, 37.0902],
                marker: [-95.7129, 37.0902],
                zoom: 3,
            });
            fetch(`https://www.techconnect.expert/events/searchEvents?location=${userInput}`)
                .then(res => {
                    res.json().then(events => {
                        events.forEach(event => {
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(3)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                html += `<div class="card">`
                                html += `<div class="card-body">`
                                html += `<a href="/event/${event.id}/reviews"><h5>${event.title}</h5></a>`
                                html += `<p>${event.description}</p>`
                                html += `<p>${event.location}</p>`
                                html += `</div>`
                                html += `</div>`
                                document.getElementById("location").value = "";
                                $('.events').html(html)
                            })
                        })
                    })
                })
        })
    }

    getEventByLocation()

// --------------FIND EVENT BY INTEREST----------

    function getEventByInterest() {
        clearEventsDiv()
        let submitBtn = document.getElementById("subBtn2")
        submitBtn.addEventListener("click", function (event) {
            event.preventDefault();
            let userInput = document.getElementById("interests").value
            let html = ""
            map.remove()
            map = new mapboxgl.Map({
                container: 'map',
                style: 'mapbox://styles/mapbox/navigation-day-v1',
                center: [-95.7129, 37.0902],
                marker: [-95.7129, 37.0902],
                zoom: 3,
            });
            fetch(`https://www.techconnect.expert/events/searchInterest?interest=${userInput}`)
                .then(res => {
                    res.json().then(events => {
                        events.forEach(event => {
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(3)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                html += `<div class="card">`
                                html += `<div class="card-body">`
                                html += `<a href="/event/${event.id}/reviews"><h5>${event.title}</h5></a>`
                                html += `<p>${event.description}</p>`
                                html += `<p>${event.location}</p>`
                                html += `</div>`
                                html += `</div>`
                                document.getElementById("interests").value = "";
                                $('.events').html(html)
                            })
                        })
                    })
                })
        })
    }

    getEventByInterest()

// --------------FIND EVENT BY KEYWORDS----------

    function getEventByKeyword() {
        clearEventsDiv()
        let submitBtn = document.getElementById("subBtn3")
        submitBtn.addEventListener("click", function (event) {
            event.preventDefault();
            console.log("clicked")
            let userInput = document.getElementById("keyword").value
            let html=""
            map.remove()
            map = new mapboxgl.Map({
                container: 'map',
                style: 'mapbox://styles/mapbox/navigation-day-v1',
                center: [-95.7129, 37.0902],
                marker: [-95.7129, 37.0902],
                zoom: 3,
            });
            fetch(`https://www.techconnect.expert/events/searchKeyword?keyword=${userInput}`)
                .then(res => {
                    res.json().then(events => {
                        events.forEach(event => {
                            console.log(event)
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(3)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                html += `<div class="card">`
                                html += `<div class="card-body">`
                                html += `<a href="/event/${event.id}/reviews"><h5>${event.title}</h5></a>`
                                html += `<p>${event.description}</p>`
                                html += `<p>${event.location}</p>`
                                html += `</div>`
                                html += `</div>`
                                document.getElementById("keyword").value = "";
                                $('.events').html(html)
                            })
                        })
                    })
                })
        })
    }
    getEventByKeyword()
})