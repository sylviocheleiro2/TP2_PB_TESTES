const BASE_URL = '/api/pessoas';

async function handleFetchResponse(response) {
    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || `Erro ${response.status}`);
    }
    return response.status === 204 ? null : response.json();
}

export function getPessoas() {
    return fetch(BASE_URL).then(handleFetchResponse);
}

export function getPessoaById(id) {
    return fetch(`${BASE_URL}/${id}`).then(handleFetchResponse);
}

export function createPessoa(person) {
    return fetch(BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(person),
    }).then(handleFetchResponse);
}

export function updatePessoa(id, person) {
    return fetch(`${BASE_URL}/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(person),
    }).then(handleFetchResponse);
}

export function deletePessoa(id) {
    return fetch(`${BASE_URL}/${id}`, {
        method: 'DELETE',
    }).then(handleFetchResponse);
}
