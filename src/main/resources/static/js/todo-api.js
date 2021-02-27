apiEndpoint = location.origin + '/api/todo/';
const form = document.getElementById('register');

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
