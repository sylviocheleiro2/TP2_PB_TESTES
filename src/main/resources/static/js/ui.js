const addForm = document.getElementById('add-form');
const personList = document.getElementById('person-list');
const editModal = document.getElementById('edit-modal');
const editForm = document.getElementById('edit-form');
const closeModalButton = document.querySelector('.close-button');
const appRoot = document.getElementById('app');

// --- Notificações simples ---
function ensureNoticeHost() {
    let el = document.getElementById('notice');
    if (!el && appRoot) {
        el = document.createElement('div');
        el.id = 'notice';
        appRoot.prepend(el);
    }
    return el;
}

export function showNotice(type, message) {
    const host = ensureNoticeHost();
    if (!host) return;
    host.className = `notice notice-${type}`; // success | error | warning
    host.textContent = message || '';
    host.style.display = 'block';
}

export function clearNotice() {
    const host = ensureNoticeHost();
    if (!host) return;
    host.textContent = '';
    host.className = 'notice';
    host.style.display = 'none';
}

// --- Funções de Renderização ---

function createPersonListItem(person) {
    const listItem = document.createElement('li');
    listItem.setAttribute('data-id', person.id);
    updateListItemContent(listItem, person);
    return listItem;
}

function updateListItemContent(listItem, person) {
    listItem.innerHTML = `
        <div class="person-info">
            <strong>${person.nome}</strong>
            <small>Email: ${person.email} | CPF: ${person.cpf}</small>
        </div>
        <div class="person-actions">
            <button class="btn-edit" data-id="${person.id}">Editar</button>
            <button class="btn-delete" data-id="${person.id}">Remover</button>
        </div>
    `;
}

export function renderPessoasList(pessoas) {
    personList.innerHTML = '';
    pessoas.forEach(person => {
        const listItem = createPersonListItem(person);
        personList.appendChild(listItem);
    });
}

export function addPessoaToList(person) {
    const listItem = createPersonListItem(person);
    personList.appendChild(listItem);
}

export function updatePessoaInList(person) {
    const listItem = document.querySelector(`li[data-id='${person.id}']`);
    if (listItem) {
        updateListItemContent(listItem, person);
    }
}

export function removePessoaFromList(id) {
    const listItem = document.querySelector(`li[data-id='${id}']`);
    if (listItem) {
        listItem.remove();
    }
}

// --- Funções do Formulário e Modal ---

export function getAddFormData() {
    const nome = addForm.querySelector('#nome').value;
    const idade = parseInt(addForm.querySelector('#idade').value);
    const email = addForm.querySelector('#email').value;
    const cpf = addForm.querySelector('#cpf').value.replace(/\D/g, '');
    return { nome, idade, email, cpf };
}

export function getEditFormData() {
    const id = editForm.querySelector('#edit-id').value;
    const nome = editForm.querySelector('#edit-nome').value;
    const idade = parseInt(editForm.querySelector('#edit-idade').value);
    const email = editForm.querySelector('#edit-email').value;
    const cpf = editForm.querySelector('#edit-cpf').value.replace(/\D/g, '');
    return { id, nome, idade, email, cpf };
}

export function resetAddForm() {
    addForm.reset();
}

export function openEditModal(person) {
    editForm.querySelector('#edit-id').value = person.id;
    editForm.querySelector('#edit-nome').value = person.nome;
    editForm.querySelector('#edit-idade').value = person.idade;
    editForm.querySelector('#edit-email').value = person.email;
    editForm.querySelector('#edit-cpf').value = person.cpf;
    editModal.style.display = 'block';
}

export function closeEditModal() {
    editModal.style.display = 'none';
}

// --- Listeners Internos do UI ---

if (closeModalButton) {
    closeModalButton.addEventListener('click', closeEditModal);
}
window.addEventListener('click', event => {
    if (event.target === editModal) {
        closeEditModal();
    }
});
