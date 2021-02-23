// const form = document.getElementById('register');

const todoApi = new TodoApi();

form.addEventListener('submit', function (event) {
    event.preventDefault();
    todoApi.postTodo();
});
