

  const date = new Date().;

console.log(formattedDate)

    fetch(`http://localhost:8080/events/closestEvents?currentDate=${currentDate}`)
        .then( response => { response.json()
                .then(events => {
                    console.log(events)
                })
        })