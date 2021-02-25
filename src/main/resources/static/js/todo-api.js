const apiEndpoint = location.origin + '/api/todo/';
const form = document.getElementById('register');

class TodoApi {

    constructor(){
        console.log('constructor');
    }

    fetch(){
        fetch(apiEndpoint)
        .then((res) => {
            if (!res.ok) {
                throw new Error('fetch failure...');
            }
            return res.json();
        })
        .then((todoList) => {
            todoList.forEach((todo) => {
                // sample
                // <a href="http://localhost:8081/detail?id=1001"> <i class="fas fa-pen-square"></i> 1001: listen to music </a>
                const li = document.createElement('li');
                const a = document.createElement('a');
                a.href = location.origin + '/detail?id=' + todo.id;
                a.innerText = `${todo.id}: ${todo.activityName}`;
                const i = document.createElement('i');
                i.classList.add('fas', 'fa-pen-square');
                a.prepend(i);
                li.append(a);
                todoUl.append(li);
            });
        })
        .catch((error) => {
            console.log('loop process error');
        });
    }

    postTodo(){
        const formData = new FormData(form);
        formData.append('progress', 'Doing');
        // @see https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Object/fromEntries
        const plainFormData = Object.fromEntries(formData.entries());
        console.log(plainFormData);
    
        // sample data
        // JSON.stringify({activityName: "test", category: "None", label: "aaaa", progress: "Doing"});
        const data = JSON.stringify(plainFormData);
        console.log(data);
    
        const myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');
    
        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: data,
            redirect: 'follow',
        };
    
        fetch(apiEndpoint, requestOptions)
            .then((response) => response.text())
            .then((result) => {
                // TODO: 作成しましたポップアップ
    
                // redirect to document root
                location.href = location.origin;
            })
            .catch((error) => console.log('error', error));
    };
}