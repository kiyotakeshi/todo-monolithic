// var requestOptions = {
//     method: 'GET'
// };

// fetch("http://127.0.0.1:8081/api/todo", requestOptions)
//     .then(function(res) {
//         console.log("status=" + res.status);
//         console.log(res.json());
// })

var requestOptions = {
    method: 'GET'
};

fetch("http://127.0.0.1:8081/api/todo", requestOptions)
    .then(function(res) {
        console.log("status = " + res.status)
        return res.json();
    })
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.log("fetch failure...");
    })
