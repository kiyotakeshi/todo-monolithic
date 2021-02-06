const url = new URL(location.href);
const id = url.searchParams.get('id');
const deleteButton = document.getElementById('delete');
const h1 = document.getElementById('h1');
// const todoUl = document.getElementById('todo');
const todoTable = document.getElementById('todo');

fetch(url.origin + '/api/todo/' + id)
    .then(res => {
        if(res.ok){
            return res.json();
        }
        throw new Error("fetch failure...");
    })
    .then(todo => {
        Object.keys(todo).forEach(key => {
            // const li = document.createElement('li');
            // li.append(key + ":" + todoValue);
            // todoUl.append(li);
            const tr = document.createElement('tr');
            const th = document.createElement('th');
            const td = document.createElement('td');
            th.append(key);
            td.append(todo[key]);
            todoTable.append(tr);
            todoTable.append(th);
            todoTable.append(td);
        })
    })
    .catch(error => {
        console.log("error");
        todoId.remove();
        todoActivityName.remove();
        todoCategory.remove();
        todoLabel.remove();
        todoProgress.remove();
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
            console.log("削除できました");
            todoId.remove();
            todoActivityName.remove();
            todoCategory.remove();
            todoLabel.remove();
            todoProgress.remove();
            deleteButton.remove();
            h1.remove();
        }
        // Todo なぜかここに入ってくる
        throw new Error("error");
    })
    // .then(result => alert(res.ok ? '成功' : '失敗');
    .catch(error => console.log('delete failure', error));
};

deleteButton.addEventListener('click', () => todoDelete());
