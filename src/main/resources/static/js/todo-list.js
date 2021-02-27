const todoUl = document.getElementById('todoUl');

const todoApi = new TodoApi();

const response = todoApi.getTodoList();

// api から取得したデータを表示
response.then((todoList) => {
    todoList.forEach((todo) => {
        const li = document.createElement('li');
        const a = document.createElement('a');
        a.href = location.origin + '/detail?id=' + todo.id;
        a.innerHTML = `${todo.id}: ${todo.activityName}`;
        a.classList.add('fas', 'fa-pen-square');
        li.append(a);
        todoUl.append(li);
    });
})
.catch((error) => {
    console.log('fetch error');
    h1.innerHTML = 'Todo の取得に失敗しました';
});
