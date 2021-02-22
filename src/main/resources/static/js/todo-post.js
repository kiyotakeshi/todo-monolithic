const url = new URL(location.href);
const form = document.getElementById('register');

const postTodo = () => {
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

    fetch(window.location.origin + '/api/todo/', requestOptions)
        .then((response) => response.text())
        .then((result) => {
            // TODO: 作成しましたポップアップ

            // redirect to document root
            location.href = url.origin;
        })
        .catch((error) => console.log('error', error));
};

form.addEventListener('submit', function (event) {
    event.preventDefault();
    postTodo();
});
