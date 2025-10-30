const form = document.getElementById('login-form');
const emailInput = document.getElementById('login-email');
const passwordInput = document.getElementById('login-password');
const errorEl = document.getElementById('login-error');

function showError(msg) {
  errorEl.textContent = msg;
  errorEl.style.display = 'block';
}

function clearError() {
  errorEl.textContent = '';
  errorEl.style.display = 'none';
}

function isValidEmail(email) {
  // Validação simples de email no frontend
  const re = /^[\w._%+-]+@[\w.-]+\.[A-Za-z]{2,}$/;
  return re.test(String(email).toLowerCase());
}

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  clearError();

  const email = emailInput.value.trim();
  const password = passwordInput.value;

  if (!email || !password) {
    showError('Informe email e senha.');
    return;
  }

  if (!isValidEmail(email)) {
    showError('Email inválido.');
    return;
  }

  try {
    const resp = await fetch('/api/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    });

    if (resp.ok) {
      window.location.href = '/cadastro.html';
    } else {
      try {
        const data = await resp.json();
        showError(data?.message || 'Falha no login.');
      } catch (_) {
        const text = await resp.text();
        showError(text || 'Falha no login.');
      }
    }
  } catch (err) {
    showError('Erro de rede ao tentar logar.');
  }
});
