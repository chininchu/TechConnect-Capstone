document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        themeSystem: 'bootstrap5',
        headerToolbar: {
            left: "prev,next",
            center: "title",
            right: "dayGridMonth, timeGridWeek, timeGridDay"

        },
        events: [],
        selectable: true,
        eventColor: '#378006',
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

    fetch("http://localhost:8080/events/userEvents")
        .then(response => {
            response.json().then(events => {
                    events.forEach(event => {
                        console.log(event.location);
                            var eventArr = []
                            var newEvent = {}
                            newEvent.title = event.title
                            newEvent.start = event.dataTime
                            newEvent.allDay = true
                            newEvent.color = 'blue'
                            newEvent.display = 'block'
                            newEvent.url = `http://localhost:8080/event/${event.id}/reviews`
                            calendar.addEvent(newEvent);
                            var events = calendar.getEvents();
                        })
                    })
                })
})
