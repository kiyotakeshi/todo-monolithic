const url = new URL(location.href);
const id = url.searchParams.get('id');
const deleteButton = document.getElementById('delete');
const updateButton = document.getElementById('update');
const h1 = document.getElementById('h1');
const todoUl = document.getElementById('todo');

const updateForm = document.createElement('form');
updateForm.setAttribute('class', 'form');

function createLabelDom(value) {
    const label = document.createElement('label');
    label.setAttribute('for', value);
    label.innerText = value;
    return label;
}

function createInputDom(value) {
    const input = document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('name', value);
    return input;
}

function createSelectDom(value) {
    const select = document.createElement('select');
    select.setAttribute('name', value);
    return select;
}

fetch(url.origin + '/api/todo/' + id)
    .then((res) => {
        if (res.ok) {
            return res.json();
        }
        throw new Error('fetch failure...');
    })
    .then((todo) => {
        // @see https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/Object/entries#iterating_through_an_object
        Object.entries(todo).forEach(([key, todoValue]) => {

            const li = document.createElement('li');

            function setPrimaryOptionToSelect(select, primaryValue) {
                const option = document.createElement('option');
                option.setAttribute('value', primaryValue);
                option.innerText = primaryValue;
                select.appendChild(option);
                return select;
            }

            // fetch してきた Todo の持つ value(todoValue) と異なる Enum を option の選択肢として追加
            function setOtherOptionToSelect(select, optionEnum) {
                optionEnum.filter(function (element) {
                    if (element !== todoValue) {
                        const option = document.createElement('option');
                        option.setAttribute('value', element);
                        option.innerText = element;
                        select.appendChild(option);
                    }
                });
                return select;
            }

            // id は表示するだけ(編集不可)
            if (key === 'id') {
                li.append(`${key}: ${todoValue}`);
            }

            if (key === 'activityName') {
                const label = createLabelDom(key);

                const input = createInputDom(key);
                input.required = true;
                input.value = todoValue;

                li.appendChild(label);
                li.appendChild(input);
            }

            if (key === 'progress') {
                const label = createLabelDom(key);

                let select = createSelectDom(key);

                // fetch してきた Todo の progress (todoValue) を選択済みとして表示
                select = setPrimaryOptionToSelect(select, todoValue);

                // 他の progress を option として追加
                // @see API reference (localhost:8081/api/)
                const progressEnum = ['Open', 'Doing', 'Done'];
                select = setOtherOptionToSelect(select, progressEnum);

                label.appendChild(select);
                li.appendChild(label);
            }

            if (key === 'category') {
                const label = createLabelDom(key);

                // この if のスコープ内なので、 mutable に扱う
                let select = createSelectDom(key);

                // fetch してきた Todo の category (todoValue) を選択済みとして表示
                select = setPrimaryOptionToSelect(select, todoValue);

                // 他の progress を option として追加
                // @see API reference (localhost:8081/api/)
                const categoryEnum = [
                    'Job',
                    'Housework',
                    'Hobby',
                    'Other',
                    'None',
                ];
                select = setOtherOptionToSelect(select, categoryEnum);

                label.appendChild(select);
                li.appendChild(label);
            }

            if (key === 'label') {
                const label = createLabelDom(key);

                const input = createInputDom(key);
                input.value = todoValue;

                li.appendChild(label);
                li.appendChild(input);
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

    fetch('http://localhost:8081/api/todo/' + id, requestOptions)
        .then((res) => {
            if ((res.status = 204)) {
                // redirect to document root
                location.href = url.origin;
            } else {
                throw new Error('delete failure');
            }
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

    fetch('http://localhost:8081/api/todo/' + id, requestOptions)
        .then((res) => {
            if ((res.status = 200)) {
                // TODO: 更新しましたポップアップ
                // redirect to document root
                location.href = url.origin;
            } else {
                throw new Error('update failure');
            }
        })
        .catch((error) => console.log('update failure', error));
    console.log('update start');
};

// TODO: 確認を出すようにする
deleteButton.addEventListener('click', () => todoDelete());

updateButton.addEventListener('click', () => todoUpdate());
