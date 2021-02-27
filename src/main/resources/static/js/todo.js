const apiEndpoint = location.origin + '/api/todo/';
const deleteButton = document.getElementById('delete');
const updateButton = document.getElementById('update');
const h1 = document.getElementById('h1');
const todoUl = document.getElementById('todo');

const updateForm = document.createElement('form');
updateForm.classList.add('form');

function createLabelDom(value) {
    const label = document.createElement('label');
    label.innerText = value;
    return label;
}

function createInputDom(value) {
    const input = document.createElement('input');
    input.name = value;
    return input;
}

function createSelectDom(value) {
    const select = document.createElement('select');
    select.name = value;
    return select;
}

const todoApi = new TodoApi();
const response = todoApi.getTodo(apiEndpoint);

// 取得した todo を表示
response.then((todo) => {
        // @see https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Object/entries#iterating_through_an_object
        Object.entries(todo).forEach(([key, todoValue]) => {
            const li = document.createElement('li');

            function setPrimaryOptionToSelect(select, primaryValue) {
                const option = document.createElement('option');
                option.value = primaryValue;
                option.innerText = primaryValue;
                select.appendChild(option);
                return select;
            }

            // fetch してきた Todo の持つ value(todoValue) と異なる Enum を option の選択肢として追加
            function setOtherOptionToSelect(select, optionEnum) {
                optionEnum.filter(function (element) {
                    if (element !== todoValue) {
                        const option = document.createElement('option');
                        option.value = element;
                        option.innerText = element;
                        select.appendChild(option);
                    }
                });
                return select;
            }

            switch (key) {
                case 'id':
                    // id は表示するだけ(編集不可)
                    li.append(`${key}: ${todoValue}`);
                    break;

                case 'activityName':
                    const label1 = createLabelDom(key);

                    const input1 = createInputDom(key);
                    input1.required = true;
                    input1.value = todoValue;
                    input1.classList.add('activity-name');

                    li.appendChild(label1);
                    li.appendChild(input1);
                    break;

                case 'progress':
                    const label2 = createLabelDom(key);
                    label2.innerText = key;

                    // progress の選択肢
                    // @see API reference (localhost:8081/api/)
                    const progressEnum = ['Open', 'Doing', 'Done'];

                    // fetch してきた Todo の progress (todoValue) をラジオボタンの選択済みとして表示
                    progressEnum.forEach((progress) => {
                        const input = document.createElement('input');
                        input.id = progress;
                        input.name = key;
                        input.type = 'radio';
                        if (progress === todoValue) {
                            input.checked = true;
                        }
                        input.value = progress;
                        const label = document.createElement('label');
                        label.htmlFor = input.id;
                        label.innerText = progress;
                        label2.appendChild(input);
                        label2.appendChild(label);
                    });

                    li.appendChild(label2);
                    break;

                case 'category':
                    const label3 = createLabelDom(key);

                    // この スコープ内なので、 mutable に扱う
                    let select2 = createSelectDom(key);

                    // fetch してきた Todo の category (todoValue) を選択済みとして表示
                    select2 = setPrimaryOptionToSelect(select2, todoValue);

                    // 他の category を option として追加
                    // @see API reference (localhost:8081/api/)
                    const categoryEnum = [
                        'Job',
                        'Housework',
                        'Hobby',
                        'Other',
                        'None',
                    ];
                    select = setOtherOptionToSelect(select2, categoryEnum);

                    label3.appendChild(select2);
                    li.appendChild(label3);
                    break;

                case 'label':
                    const label4 = createLabelDom(key);

                    const input2 = createInputDom(key);
                    input2.value = todoValue;

                    li.appendChild(label4);
                    li.appendChild(input2);

                    break;

                default:
                    console.log(`${key} is Invalid key`);
            }

            updateForm.append(li);
            todoUl.append(updateForm);
        });
    })
    .catch((error) => {
        todoUl.remove();
        deleteButton.remove();
        updateButton.remove();
        h1.innerHTML = '指定したIDの Todo は存在していません';
    });

deleteButton.addEventListener('click', () => {
    const result = confirm('delete?');
    if (result) {
        todoApi.deleteTodo(apiEndpoint);
    } else {
        console.log('削除しませんでした');
    }
});

updateButton.addEventListener('click', () => todoApi.updateTodo(apiEndpoint));
