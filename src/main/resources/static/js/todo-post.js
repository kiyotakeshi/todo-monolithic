const form = document.getElementById('register');

const postTodo = () => {
    const formData = new FormData(form);
    formData.append("progress", "Doing");
    // @see https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Object/fromEntries
    const plainFormData = Object.fromEntries(formData.entries());
    console.log(plainFormData);

    // sample data
    // JSON.stringify({activityName: "test", category: "None", label: "aaaa", progress: "Doing"});
    const data = JSON.stringify(plainFormData);
    console.log(data);

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    
    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: data,
        redirect: 'follow'
    };        

    fetch(window.location.origin + "/api/todo/", requestOptions)
    .then(response => response.text())
    .then(result => {
        console.log(result);
        console.log("submit!!!");
    })
    .catch(error => console.log('error', error));
};

form.addEventListener("submit", function(event){
    event.preventDefault();
    postTodo();
});        
