let allLogs = [];
let chartObj = null;

// Save / Update Log
async function saveLog() {
  let body = {
    helperName: curUser,
    createdBy: curUser,
    helpedTo: document.getElementById('helpedTo').value,
    issueType: document.getElementById('issueType').value,
    description: document.getElementById('description').value,
    timeSpentMinutes: parseInt(document.getElementById('timeSpentMinutes').value) || 0
  };

  if (!body.helpedTo || !body.timeSpentMinutes) {
    alert('HelpedTo & Time required bro!');
    return;
  }

  let idVal = document.getElementById('id').value;
  if (idVal) {
    await fetch('/api/logs/' + idVal, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
    document.getElementById('id').value = '';
    document.getElementById('saveBtn').innerText = '💾 Save My Help';
  } else {
    await fetch('/api/logs', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
  }

  document.getElementById('helpedTo').value = '';
  document.getElementById('description').value = '';
  document.getElementById('timeSpentMinutes').value = '';
  loadLogs();
}

function editLog(log) {
  if (curRole !== 'ADMIN' && log.createdBy !== curUser && log.helperName !== curUser) {
    alert('Neevi matrame edit cheyochu bro!');
    return;
  }
  document.getElementById('id').value = log.id;
  document.getElementById('helpedTo').value = log.helpedTo;
  document.getElementById('issueType').value = log.issueType;
  document.getElementById('description').value = log.description;
  document.getElementById('timeSpentMinutes').value = log.timeSpentMinutes;
  document.getElementById('saveBtn').innerText = '✏️ Update';
  window.scrollTo(0, 200);
}

async function deleteLog(lid) {
  if (curRole !== 'ADMIN') {
    alert('Delete ONLY Admin bro!');
    return;
  }
  if (confirm('Delete this log?')) {
    await fetch('/api/logs/' + lid, { method: 'DELETE' });
    loadLogs();
  }
}

async function loadLogs() {
  let r = await fetch('/api/logs');
  allLogs = await r.json();
  let filtered = curRole === 'ADMIN' ? allLogs : allLogs.filter(l => (l.createdBy || l.helperName) === curUser);
  renderLogs(filtered);
}

function renderLogs(logs) {
  let h = '<tr><th>Helper</th><th>Helped To</th><th>Issue</th><th>Time</th><th>Action</th></tr>';
  logs.slice().reverse().forEach(l => {
    h += `<tr>
      <td><span class="clickable" onclick="openProfile('${l.helperName}')">${l.helperName}</span></td>
      <td><span class="clickable" onclick="openProfile('${l.helpedTo}')">${l.helpedTo}</span></td>
      <td>${l.issueType}</td><td>${l.timeSpentMinutes}m</td>
      <td>
        <button style="background:#f59e0b;color:#fff" onclick='editLog(${JSON.stringify(l).replace(/'/g, "&#39;")})'>Edit</button>
        ${curRole === 'ADMIN' ? `<button style="background:#ef4444;color:#fff" onclick="deleteLog(${l.id})">Del</button>` : ''}
      </td></tr>`;
  });
  if (logs.length === 0) h += '<tr><td colspan=5 style="text-align:center">No data yet</td></tr>';
  document.getElementById('logsTable').innerHTML = h;
  document.getElementById('totalHelps').innerText = logs.length;
  let m = logs.reduce((s, l) => s + (l.timeSpentMinutes || 0), 0);
  document.getElementById('totalHours').innerText = Math.floor(m / 60) + 'h ' + (m % 60) + 'm';
}

// --- ADMIN ANALYTICS - Avg Capability ---
function openProfile(name) {
  if (!name) return;
  name = name.toLowerCase().trim();
  if (!name) return;

  let selfLogs = allLogs.filter(l => (l.helperName || '').toLowerCase() === name && (l.issueType || '').toLowerCase() === 'self work');
  let givenLogs = allLogs.filter(l => (l.helperName || '').toLowerCase() === name && (l.issueType || '').toLowerCase() !== 'self work');
  let takenLogs = allLogs.filter(l => (l.helpedTo || '').toLowerCase() === name && (l.helperName || '').toLowerCase() !== name);

  let selfTime = selfLogs.reduce((s, l) => s + (l.timeSpentMinutes || 0), 0);
  let givenTime = givenLogs.reduce((s, l) => s + (l.timeSpentMinutes || 0), 0);
  let takenTime = takenLogs.reduce((s, l) => s + (l.timeSpentMinutes || 0), 0);

  let totalDone = selfTime + givenTime;
  let totalAll = totalDone + takenTime;
  let capability = totalAll > 0 ? (totalDone / totalAll * 100).toFixed(1) : (selfLogs.length > 0 ? 100 : 0);

  document.getElementById('profileName').innerText = '👤 ' + name.toUpperCase() + ' - Analytics';
  document.getElementById('profileStats').innerHTML = `
    <b>✅ Own Work (Self):</b> ${selfLogs.length} tasks | <b>${selfTime} mins</b><br>
    <b>🤝 Help Given:</b> ${givenLogs.length} times | <b>${givenTime} mins</b> → To: ${[...new Set(givenLogs.map(l => l.helpedTo))].join(', ') || 'None'}<br>
    <b>🙏 Help Taken:</b> ${takenLogs.length} times | <b>${takenTime} mins</b> ← From: ${[...new Set(takenLogs.map(l => l.helperName))].join(', ') || 'None'}<br>
    <hr>
    <b>💪 Avg Capability = (Own+Given)/(Own+Given+Taken)*100 = (${selfTime}+${givenTime})/(${totalAll || 1})*100 = <span style="color:#4f46e5;font-size:18px">${capability}%</span></b><br>
    <small>Own ekkuva & help isthe capability peruguthundi. Help teesukunte thagguthundi.</small>
  `;

  let lh = '<table><tr><th>Type</th><th>With</th><th>Issue</th><th>Time</th></tr>';
  selfLogs.forEach(l => lh += `<tr><td style="color:#4f46e5">Self</td><td>-</td><td>${l.issueType}</td><td>${l.timeSpentMinutes}m</td></tr>`);
  givenLogs.forEach(l => lh += `<tr><td style="color:#10b981">Given</td><td>${l.helpedTo}</td><td>${l.issueType}</td><td>${l.timeSpentMinutes}m</td></tr>`);
  takenLogs.forEach(l => lh += `<tr><td style="color:#f59e0b">Taken</td><td>${l.helperName}</td><td>${l.issueType}</td><td>${l.timeSpentMinutes}m</td></tr>`);
  lh += '</table>';
  document.getElementById('profileLogs').innerHTML = lh;

  if (chartObj) chartObj.destroy();
  let ctx = document.getElementById('pieChart').getContext('2d');
  chartObj = new Chart(ctx, {
    type: 'doughnut',
    data: { labels: ['Own Work', 'Help Given', 'Help Taken'], datasets: [{ data: [selfTime, givenTime, takenTime], backgroundColor: ['#4f46e5', '#10b981', '#f59e0b'] }] },
    options: { plugins: { legend: { position: 'bottom' } } }
  });

  document.getElementById('profileModal').style.display = 'flex';
}

function closeModal() {
  document.getElementById('profileModal').style.display = 'none';
}