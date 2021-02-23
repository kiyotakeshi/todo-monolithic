const apiEndpoint = location.origin + '/api/todo/';
const id = new URL(location.href).searchParams.get('id');
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

fetch(apiEndpoint + id)
    .then((res) => {
        if (!res.ok) {
            throw new Error('fetch failure...');
        }
        return res.json();
    })
    .then((todo) => {
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
        console.log('error');
        todoUl.remove();
        deleteButton.remove();
        updateButton.remove();
        h1.innerHTML = '指定したIDの Todo は存在していません';
    });

const todoDelete = () => {
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
};

const todoUpdate = () => {
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
            // TODO: 更新しましたポップアップ
            // redirect to document root
            location.href = location.origin;
        })
        .catch((error) => console.log('update failure', error));
};

deleteButton.addEventListener('click', () => {
    const result = confirm('delete?');
    if (result) {
        todoDelete();
    } else {
        console.log('削除しませんでした');
    }
});

updateButton.addEventListener('click', () => todoUpdate());
