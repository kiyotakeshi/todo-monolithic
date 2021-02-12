const url = new URL(location.href);
const id = url.searchParams.get('id');
const deleteButton = document.getElementById('delete');
const h1 = document.getElementById('h1');
const todoUl = document.getElementById('todo');

fetch(url.origin + '/api/todo/' + id)
    .then(res => {
        if(res.ok){
            return res.json();
        }
        throw new Error("fetch failure...");
    })
    .then(todo => {
        // @see https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Object/entries#iterating_through_an_object
        Object.entries(todo).forEach(([key, value]) => {
            const li = document.createElement('li');
            li.append(`${key}: ${value}`);
            todoUl.append(li);
        })
    })
    .catch(error => {
        console.log("error");
        todoUl.remove();
        deleteButton.remove();
        h1.innerHTML = "指定したIDの Todo は存在していません";
    })

var requestOptions = {
    method: 'DELETE'
    };
    
const todoDelete = () => {
    fetch("http://localhost:8081/api/todo/" + id, requestOptions)
    .then(res => {
        if(res.status = 204) {
            // redirect to document root
            location.href = url.origin
        } else {
            throw new Error("delete failure");
        }
    })
    .catch(error => console.log('delete failure', error));
};

// TODO: 確認を出すようにする
deleteButton.addEventListener('click', () => todoDelete());

