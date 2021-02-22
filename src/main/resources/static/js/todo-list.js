const origin = new URL(location.href).origin;
const todoUl = document.getElementById('todoUl');

fetch(origin + '/api/todo')
    .then((res) => {
        if (res.ok) {
            return res.json();
        }
        throw new Error('fetch failure...');
    })
    .then((todoList) => {
        todoList.forEach((todo) => {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.href = origin + '/detail?id=' + todo.id;
            a.innerText = `${todo.id}: ${todo.activityName}`;
            li.append(a);
            todoUl.append(li);
        });
    })
    .catch((error) => {
        console.log('loop process error');
    });
