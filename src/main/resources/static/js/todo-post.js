const apiEndpoint = location.origin + '/api/todo/';
const todoApi = new TodoApi();

form.addEventListener('submit', function (event) {
    event.preventDefault();
    todoApi.postTodo(apiEndpoint);
});
