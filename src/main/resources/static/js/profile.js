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
    calendar.render()

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


    // USERS EVENTS ON MAP

    fetch("/events/userEvents")
        .then(response => {
            response.json().then(events => {
                    events.forEach(event => {
                        console.log(event.location);
                        geocode(event.location,MAPBOXAP_TOK).then(function (result) {
                            let mapCenter = ([result[0], result[1]])
                            map.setCenter(mapCenter);
                            map.setZoom(8)
                            new mapboxgl.Marker().setLngLat(mapCenter).addTo(map);
                            new mapboxgl.Popup().setLngLat(mapCenter).setHTML("<p>" + event.title + "</p>").addTo(map)
                            var eventArr = []
                            var newEvent = {}
                            newEvent.title = event.title
                            newEvent.start = event.dataTime
                            newEvent.allDay = true
                            newEvent.color = 'blue'
                            newEvent.display = 'block'

                            calendar.addEvent(newEvent);
                            var events = calendar.getEvents();
                        })
                    })
                })
        })
})
