let curUser = null;
let curRole = null;

// Page load ayyaka admin check
document.addEventListener('DOMContentLoaded', () => {
  const loginUserInput = document.getElementById('loginUser');
  if (loginUserInput) {
    loginUserInput.addEventListener('input', () => {
      const pinBox = document.getElementById('loginPin');
      if (loginUserInput.value.toLowerCase() === 'admin') {
        pinBox.style.display = 'block';
      } else {
        pinBox.style.display = 'none';
      }
    });
  }
});

// Login Logic
async function doLogin() {
  let res = await fetch('/api/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      username: document.getElementById('loginUser').value,
      password: document.getElementById('loginPass').value,
      pin: document.getElementById('loginPin').value
    })
  });
  let data = await res.json();
  if (data.success) {
    curUser = data.username;
    curRole = data.role;
    document.getElementById('loginBox').style.display = 'none';
    document.getElementById('mainBox').style.display = 'block';
    document.getElementById('who').innerText = '👤 ' + curUser + ' (' + curRole + ')';
    if (curRole === 'ADMIN') {
      document.getElementById('adminBar').style.display = 'block';
      loadRequests(); // admin ki requests kanipisthayi
    }
    loadLogs();
  } else {
    document.getElementById('loginMsg').innerText = data.msg;
  }
}

function logout() {
  location.reload();
}

// Admin Direct Create User
async function adminCreateUser() {
  let u = document.getElementById('newU').value.toLowerCase().trim();
  let p = document.getElementById('newP').value.trim();
  if (!u || !p) {
    alert('Username & Password kavali bro!');
    return;
  }
  let res = await fetch('/api/auth/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username: u, password: p, role: 'USER' })
  });
  let d = await res.json();
  alert(d.msg);
  document.getElementById('newU').value = '';
  document.getElementById('newP').value = '';
}