const url = new URL(location.href);
const id = url.searchParams.get('id');
const todoId = document.getElementById('todoId');
const todoActivityName = document.getElementById('todoActivityName');
const todoCategory = document.getElementById('todoCategory');
const todoLabel = document.getElementById('todoLabel');
const todoProgress = document.getElementById('todoProgress');
const deleteButton = document.getElementById('delete');
const h1 = document.getElementById('h1');

fetch(url.origin + '/api/todo/' + id)
    .then(res => {
        if(res.ok){
            return res.json();
        }
        throw new Error("fetch failure...");
    })
    .then(todo => {
        console.log(todo);
        // TODO: attribute を順に取り出して処理とかできそう
        const idTd = document.createElement('td');
        idTd.append(todo.id);
        todoId.append(idTd);

        const activityNameTd = document.createElement('td');
        activityNameTd.append(todo.activityName);
        todoActivityName.append(activityNameTd);

        const progressTd = document.createElement('td');
        progressTd.append(todo.progress);
        todoProgress.append(progressTd);

        const categoryTd = document.createElement('td');
        categoryTd.append(todo.category);
        todoCategory.append(categoryTd);

        const labelTd = document.createElement('td');
        labelTd.append(todo.label);
        todoLabel.append(labelTd);
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
