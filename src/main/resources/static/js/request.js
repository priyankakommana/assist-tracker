// Modal Open/Close
function openRequestModal() {
  document.getElementById('requestModal').style.display = 'flex';
}
function closeRequestModal() {
  document.getElementById('requestModal').style.display = 'none';
}

// Send Request to Manager
async function submitRequest() {
  let body = {
    fullName: document.getElementById('reqName').value.trim(),
    requestedUsername: document.getElementById('reqUsername').value.toLowerCase().trim(),
    email: document.getElementById('reqEmail').value.trim(),
    reason: document.getElementById('reqReason').value.trim()
  };

  if (!body.fullName || !body.requestedUsername || !body.email) {
    document.getElementById('reqMsg').style.color = 'red';
    document.getElementById('reqMsg').innerText = 'All fields required bro!';
    return;
  }

  document.getElementById('reqMsg').style.color = '#4f46e5';
  document.getElementById('reqMsg').innerText = 'Sending...';

  try {
    let r = await fetch('/api/requests', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    let d = await r.json();

    if (d.success) {
      document.getElementById('reqMsg').style.color = 'green';
      document.getElementById('reqMsg').innerHTML = '✅ ' + d.msg + '<br>📧 Manager ki mail open avthundi...';
      
      // Real mailto open avthundi
      window.open(`mailto:manager@company.com?subject=Access Request - ${body.fullName}&body=Name: ${body.fullName}%0AUsername: ${body.requestedUsername}%0AEmail: ${body.email}%0AReason: ${body.reason}`, '_blank');
      
      setTimeout(closeRequestModal, 2000);
    }
  } catch (e) {
    document.getElementById('reqMsg').innerText = 'Server not running? Backend restart chey bro';
  }
}

// Admin - Pending Requests chupisthundi
async function loadRequests() {
  if (curRole !== 'ADMIN') return;
  let r = await fetch('/api/requests');
  let list = await r.json();
  let pending = list.filter(x => x.status === 'PENDING');
  let box = document.getElementById('adminRequests');
  
  if (!box) return;

  if (pending.length === 0) {
    box.innerHTML = '<b>✅ No pending access requests</b>';
    return;
  }

  let h = `<h4 style="margin:0 0 8px 0">📩 Pending Requests (${pending.length})</h4><table><tr><th>Name</th><th>Username</th><th>Email</th><th>Action</th></tr>`;
  pending.forEach(req => {
    h += `<tr><td>${req.fullName}</td><td>${req.requestedUsername}</td><td style="font-size:11px">${req.email}</td><td><button style="background:#10b981;color:#fff" onclick="approveReq(${req.id})">Approve</button></td></tr>`;
  });
  h += '</table>';
  box.innerHTML = h;
}

// Admin Approve chesthe user auto-create avthadu
async function approveReq(id) {
  let pwd = prompt('Set password (default: username+123):');
  let res = await fetch('/api/requests/' + id + '/approve', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ password: pwd || '' })
  });
  let d = await res.json();
  alert(d.msg);
  loadRequests();
}