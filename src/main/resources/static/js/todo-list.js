const todoUl = document.getElementById('todoUl');

const todoApi = new TodoApi();
const response = todoApi.fetch();

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
// エラー表示を画面に出したい場合は使う
// .catch((error) => {
//     console.log('fetch error');
// });
