const apiEndpoint = location.origin + '/api/todo/';
const todoUl = document.getElementById('todoUl');

fetch(apiEndpoint)
    .then((res) => {
        if (!res.ok) {
            throw new Error('fetch failure...');
        }
        return res.json();
    })
    .then((todoList) => {
        todoList.forEach((todo) => {
            const li = document.createElement('li');
            const a = document.createElement('a');
            a.href = location.origin + '/detail?id=' + todo.id;
            a.innerText = `${todo.id}: ${todo.activityName}`;
            li.append(a);
            todoUl.append(li);
        });
    })
    .catch((error) => {
        console.log('loop process error');
    });
