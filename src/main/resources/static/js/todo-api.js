apiEndpoint = location.origin + '/api/todo/';
const form = document.getElementById('register');

const id = new URL(location.href).searchParams.get('id');

class TodoApi {
    constructor() {
        // console.log('constructor');
    }

    getTodoList() {
        return fetch(apiEndpoint)
            .then((res) => {
                if (res.status !== 200 || !res.ok) {
                    throw new Error();
                }
                return res.json();
            })
            .catch((error) => {
                console.log('fetch error');
            });
    }

    getTodo() {
        return fetch(apiEndpoint + id)
            .then((res) => {
                if (!res.ok) {
                    throw new Error('fetch failure...');
                }
                return res.json();
            })
            .catch((error) => {
                console.log('fetch error');
            });
    }

    deleteTodo() {
        const requestOptions = {
            method: 'DELETE',
        };

        fetch(apiEndpoint + id, requestOptions)
            .then((res) => {
                if (!res.status === 204) {
                    throw new Error('delete failure');
                }
                // redirect to document root
                location.href = location.origin;
            })
            .catch((error) => console.log('delete failure', error));
    }

    updateTodo() {
        const myHeaders = new Headers();
        myHeaders.append('Content-Type', 'application/json');

        const formData = new FormData(updateForm);
        formData.append('id', id);
        const plainFormData = Object.fromEntries(formData.entries());

        // sample data
        // JSON.stringify({"id":10034,"activityName":"update","progress":"Doing","category":"Housework","label":"update label"});
        const data = JSON.stringify(plainFormData);

        const requestOptions = {
            method: 'PUT',
            headers: myHeaders,
            body: data,
            redirect: 'follow',
        };

        fetch(apiEndpoint + id, requestOptions)
            .then((res) => {
                if (!res.status === 200) {
                    throw new Error('update failure');
                }
                // redirect to document root
                location.href = location.origin;
            })
            .catch((error) => console.log('update failure', error));
    }

    postTodo() {
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
                // redirect to document root
                location.href = location.origin;
            })
            .catch((error) => console.log('error', error));
    }
}
