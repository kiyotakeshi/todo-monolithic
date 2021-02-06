const url = new URL(location.href);
const id = url.searchParams.get('id');
const todoId = document.getElementById('todoId');
const todoActivityName = document.getElementById('todoActivityName');
const todoCategory = document.getElementById('todoCategory');
const todoLabel = document.getElementById('todoLabel');
const todoProgress = document.getElementById('todoProgress');
console.log(id);
fetch(url.origin + '/api/todo/' + id)
    .then(res => {
        if(res.ok){
            return res.json();
        }
        throw new Error("fetch failure...");
    })
    .then(todo => {
        todoId.append(todo.id);
        todoActivityName.append(todo.activityName);
        todoCategory.append(todo.category);
        todoLabel.append(todo.label);
        todoProgress.append(todo.progress);
    })
    .catch(error => {
        console.log("error");
    })
