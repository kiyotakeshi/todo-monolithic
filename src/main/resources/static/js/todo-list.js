fetch('http://localhost:8081/api/todo')
    .then(res => {
        if(res.ok){
            return res.json();
        }
        throw new Error("fetch failure...");
    })
    .then(json => {
        const todoUl = document.getElementById('todoUl');
        json.forEach(todo => {
            const li = document.createElement('li');
            li.innerText = "id: "  + todo.id + " activityName: " + todo.activityName;
            // li.innerText = "id: " + todo.id;
            // li.innerText += " activityName: " + todo.activityName;
            todoUl.append(li);
        })
    })
    .catch(error => {
        console.log("loop process error");
    })
