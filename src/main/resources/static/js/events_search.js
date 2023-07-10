document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    let eventClick;
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
        eventClick:function(info) {
            info.jsEvent.preventDefault(); // don't let the browser navigate

            if (info.event.url) {
                window.open(info.event.url);
            }
        },
        dayMaxEventRows: true, // for all non-TimeGrid views
        views: {
            timeGrid: {
                dayMaxEventRows: 6,


            }
        }
    })

calendar.render()

// -----------PAGE MAP--------------
    mapboxgl.accessToken = MAPBOXAP_TOK;
    const coordinates = document.getElementById('coordinates');
    const map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/navigation-day-v1',
        center: [-95.7129, 37.0902],
        marker: [-95.7129, 37.0902],
        zoom: 3,
    });
    let marker = new mapboxgl.Marker({})
        .setLngLat([-95.7129, 37.0902])
        .addTo(map);




// ---------------SHOWS ALL EVENTS ON MAP & CALENDAR-----------
//     fetch("/events/allEvents")
//         .then( response => { response.json()
//                 .then(events => {
//                     events.forEach(event => {
//                         console.log(event.location);
//                         geocode(event.location, MAPBOXAP_TOK).then(function (result) {
//                             let mapCenter = ([result[0], result[1]])
//                             map.setCenter(mapCenter);
//                             map.setZoom(8)
//                             new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
//                             new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
//                             var eventArr = []
//                             var newEvent = {}
//                             newEvent.title = event.title
//                             newEvent.start = event.dataTime
//                             newEvent.allDay = true
//                             newEvent.color = 'blue'
//                             newEvent.display = 'block'
//
//                             calendar.addEvent(newEvent);
//                             var events = calendar.getEvents();
//                         })
//                     })
//                 })
//         })

// --------------FIND EVENT BY LOCATION----------
    function getEventByLocation() {
        let submitBtn = document.getElementById("subBtn")
        submitBtn.addEventListener("click", function (event) {
            event.preventDefault();
            let userInput = document.getElementById("location").value
            fetch(`https://www.techconnect.expert/events/searchEvents?location=${userInput}`)
                .then(res => {
                    res.json().then(events => {
                        events.forEach(event => {
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(5)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                var eventArr = []
                                var newEvent = {}
                                newEvent.title = event.title
                                newEvent.start = event.dataTime
                                newEvent.allDay = true
                                newEvent.color = 'blue'
                                newEvent.display = 'block'
                                newEvent.url = `https://www.techconnect.expert/event/${event.id}/reviews`
                                calendar.addEvent(newEvent);
                                var events = calendar.getEvents();
                                document.getElementById("location").value = "";

                            })
                        })
                    })
                })
        })
    }

    getEventByLocation()

// --------------FIND EVENT BY INTEREST----------

    function getEventByInterest() {
        let submitBtn = document.getElementById("subBtn2")
        submitBtn.addEventListener("click", function (event) {
            event.preventDefault();
            let userInput = document.getElementById("interests").value
            fetch(`https://www.techconnect.expert/events/searchInterest?interest=${userInput}`)
                .then(res => {
                    res.json().then(events => {
                        events.forEach(event => {
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(5)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                var eventArr = []
                                var newEvent = {}
                                newEvent.title = event.title
                                newEvent.start = event.dataTime
                                newEvent.allDay = true
                                newEvent.color = 'blue'
                                newEvent.display = 'block'
                                newEvent.url = `https://www.techconnect.expert/event/${event.id}/reviews`
                                calendar.addEvent(newEvent);
                                var events = calendar.getEvents();
                                document.getElementById("interests").value = "";

                            })
                        })
                    })
                })
        })
    }

    getEventByInterest()

// --------------FIND EVENT BY KEYWORDS----------

    function getEventByKeyword() {
        let submitBtn = document.getElementById("subBtn3")
        submitBtn.addEventListener("click", function (event) {
            console.log("clicked")
            event.preventDefault();
            let userInput = document.getElementById("keyword").value
            fetch(`https://www.techconnect.expert/events/searchKeyword?keyword=${userInput}`)
                .then(res => {
                    res.json().then(events => {
                        events.forEach(event => {
                            console.log(event)
                            geocode(event.location, MAPBOXAP_TOK).then(function (result) {
                                let mapCenter = ([result[0], result[1]])
                                map.setCenter(mapCenter);
                                map.setZoom(5)
                                new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                                new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                                var eventArr = []
                                var newEvent = {}
                                newEvent.title = event.title
                                newEvent.start = event.dataTime
                                newEvent.allDay = true
                                newEvent.color = 'blue'
                                newEvent.display = 'block'
                                newEvent.url = `https://www.techconnect.expert/event/${event.id}/reviews`
                                calendar.addEvent(newEvent);
                                var events = calendar.getEvents();
                                document.getElementById("keyword").value = "";

                            })
                        })
                    })
                })
        })
    }
    getEventByKeyword()

})