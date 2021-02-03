const todoUl = document.getElementById('todoUl');
fetch(document.URL + '/api/todo')
    .then(res => {
        if(res.ok){
            return res.json();
        }
        throw new Error("fetch failure...");
    })
    .then(todoList => {
        todoList.forEach(todo => {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.href = document.URL + '/api/todo/' + todo.id;
            a.innerText = `${todo.id}: ${todo.activityName}`;
            li.append(a);
            todoUl.append(li);
        })
    })
    .catch(error => {
        console.log("loop process error");
    })
