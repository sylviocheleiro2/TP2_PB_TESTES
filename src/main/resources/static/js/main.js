import * as api from './apiService.js';
import * as ui from './ui.js';

function handleError(error) {
    console.error('Ocorreu um erro:', error);
    alert(`Erro: ${error.message}`);
}

async function handleAddSubmit(event) {
    event.preventDefault();
    const personData = ui.getAddFormData();
    try {
        const newPerson = await api.createPessoa(personData);
        ui.addPessoaToList(newPerson);
        ui.resetAddForm();
    } catch (error) {
        handleError(error);
    }
}

async function handleListClick(event) {
    const target = event.target;
    const id = target.dataset.id;

    if (!id) return;

    if (target.classList.contains('btn-delete')) {
        if (confirm('Tem certeza que deseja remover esta pessoa?')) {
            try {
                await api.deletePessoa(id);
                ui.removePessoaFromList(id);
            } catch (error) {
                handleError(error);
            }
        }
    }

    if (target.classList.contains('btn-edit')) {
        try {
            const person = await api.getPessoaById(id);
            ui.openEditModal(person);
        } catch (error) {
            handleError(error);
        }
    }
}

async function handleEditSubmit(event) {
    event.preventDefault();
    const personData = ui.getEditFormData();
    try {
        const updatedPerson = await api.updatePessoa(personData.id, personData);
        ui.updatePessoaInList(updatedPerson);
        ui.closeEditModal();
    } catch (error) {
        handleError(error);
    }
}

async function initializeApp() {
    document.getElementById('add-form').addEventListener('submit', handleAddSubmit);
    document.getElementById('person-list').addEventListener('click', handleListClick);
    document.getElementById('edit-form').addEventListener('submit', handleEditSubmit);

    try {
        const pessoas = await api.getPessoas();
        ui.renderPessoasList(pessoas);
    } catch (error) {
        handleError(error);
    }
}

// Inicia a aplicação
initializeApp();
