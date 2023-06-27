"use strict";


document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        themeSystem: 'bootstrap5',
        headerToolbar: {
            left: "prev,next",
            center: "title",
            right: "dayGridMonth, timeGridWeek, timeGridDay ,list"

        },
        events: [],
        selectable: true,
        dayMaxEventRows: true, // for all non-TimeGrid views
        views: {
            timeGrid: {
                dayMaxEventRows: 6,


            }
        }
    })


    calendar.render();

    //
    // fetch("/events/allEvents")
    //     .then( response => { response.json()
    //         .then( events => {
    //             events.forEach(event => {
    //                 console.log(event);
    //                 var eventArr = []
    //                 var newEvent = {}
    //                 newEvent.title = event.title
    //                 newEvent.start = event.dataTime
    //                 newEvent.allDay = true
    //                 newEvent.color = 'blue'
    //                 newEvent.display = 'block'
    //
    //                 calendar.addEvent(newEvent);
    //                 var events = calendar.getEvents();
    //
    //
    //             });
    //         });
    //     });

// user calendar

    fetch("/events/userEvents")
        .then(response => {
            response.json()
                .then(events => {
                    events.forEach(event => {
                        console.log(event);
                        var eventArr = []
                        var newEvent = {}
                        newEvent.title = event.title
                        newEvent.start = event.dataTime
                        newEvent.allDay = true
                        newEvent.color = 'blue'
                        newEvent.display = 'block'

                        calendar.addEvent(newEvent);
                        var events = calendar.getEvents();
                    });
                });
        });


    $(".rsvpBtn").click(function () {
        console.log('clicked')
        var title = document.getElementById("exampleFormControlInput1").value
        var startTime = document.getElementById("eventstart-time").value
        var endTime = document.getElementById("eventend-time").value
        console.log(title)
        console.log(startTime)
        console.log(endTime)

        var newEvent = {}
        newEvent.title = title
        newEvent.start = startTime
        newEvent.end = endTime
        newEvent.allDay = false
        newEvent.color = 'blue'
        newEvent.display = 'block'

        calendar.addEvent(newEvent);

        var events = calendar.getEvents();

    })


    mapboxgl.accessToken = MAPBOXAP_TOK;
    const coordinates = document.getElementById('coordinates');
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/marie5646/clg37gvt0000501qdn8n0spad',
        center: [-95.7129, 37.0902],
        marker: [-95.7129, 37.0902],
        zoom: 3,
    });
    let marker = new mapboxgl.Marker({})
        .setLngLat([-95.7129, 37.0902])
        .addTo(map);


// user event/location

    fetch("/events/userEvents")
        .then(response => {
            response.json()
                .then(events => {
                    events.forEach(event => {
                        console.log(event.location);
                        geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                            let mapCenter = ([result[0], result[1]])
                            map.setCenter(mapCenter);
                            map.setZoom(8)
                            new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                            new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                        })
                    })
                })
        })

// shows all events

    // fetch("/events/allEvents")
    //     .then( response => {
    //         response.json()
    //             .then(events => {
    //                 events.forEach(event => {
    //                     console.log(event.location);
    //                     geocode(event.location, MAPBOXAP_TOK).then(function (result) {
    //                         let mapCenter = ([result[0], result[1]])
    //                         map.setCenter(mapCenter);
    //                         map.setZoom(8)
    //                         new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
    //                         new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
    //                     })
    //                 })
    //             })
    //     })


    // fetch("http://localhost:8080/events/searchEvents")
    //     .then(response => response.json())
    //     .then(events => {
    // events.forEach(event => {
    //     console.log(event.location);
    //     geocode(event.location, MAPBOXAP_TOK).then(function (result) {
    //         let mapCenter = ([result[0], result[1]])
    //         map.setCenter(mapCenter);
    //         map.setZoom(8)
    //         new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
    //         new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
    //     })
    //         console.log(events)
    //     })

    fetch(`http://localhost:8080/events/searchEvents?location=${eventLocation}}`)
        .then(res => res.json())
        .then(data => console.log(data));

    function updateMarker() {
        document.getElementById("rsvp").addEventListener("click", function () {
            //geocoder function will return a promise, this promise has our location
            let currentLocation = geocode(document.getElementById("address").value, MAPBOXAP_TOK);
            currentLocation.then(function (results) {
                map.setCenter([results[0], results[1]])
                map.setZoom(8)
                marker.setLngLat([results[0], results[1]]).addTo(map);
            })

        })
        document.getElementById("sub").addEventListener("click", function () {
            //geocoder function will return a promise, this promise has our location
            let currentLocation = geocode(document.getElementById("search").value, MAPBOXAP_TOK);
            currentLocation.then(function (results) {
                map.setCenter([results[0], results[1]])
                map.setZoom(8)
                marker.setLngLat([results[0], results[1]]).addTo(map);
            })

        })

    }

    updateMarker()


})

