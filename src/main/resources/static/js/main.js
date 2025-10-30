import * as api from './apiService.js';
import * as ui from './ui.js';

function handleError(error) {
    console.error('Ocorreu um erro:', error);
    ui.showNotice('error', error?.message || 'Ocorreu um erro inesperado.');
}

async function handleAddSubmit(event) {
    event.preventDefault();
    const personData = ui.getAddFormData();
    try {
        const newPerson = await api.createPessoa(personData);
        ui.addPessoaToList(newPerson);
        ui.resetAddForm();
        ui.showNotice('success', 'Pessoa adicionada com sucesso.');
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
                ui.showNotice('success', 'Pessoa removida.');
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
    const personData = ui.getEditFormData(); // Pega todos os dados, incluindo o ID

    // Separa o ID do resto dos dados que serão enviados no corpo da requisição
    const { id, ...updateData } = personData;

    try {
        // Envia o ID na URL e o resto dos dados no corpo
        const updatedPerson = await api.updatePessoa(id, updateData);
        ui.updatePessoaInList(updatedPerson);
        ui.closeEditModal();
        ui.showNotice('success', 'Pessoa atualizada.');
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
        ui.clearNotice();
    } catch (error) {
        handleError(error);
    }
}

// Inicia a aplicação
initializeApp();
