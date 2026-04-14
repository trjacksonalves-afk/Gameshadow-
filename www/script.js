let pc = JSON.parse(localStorage.getItem("p")) || [];

function trocarAba(id) {
    document.querySelectorAll('.aba').forEach(a => a.classList.remove('ativa'));
    document.getElementById(id).classList.add('ativa');
}

function addEXE(event) {
    let f = event.target.files[0];
    if(!f) return;
    pc.push({nome: f.name});
    localStorage.setItem("p", JSON.stringify(pc));
    render();
}

function render() {
    let l = document.getElementById("lista");
    l.innerHTML = pc.map((g, i) => `
        <div class="card" onclick="runPC(${i})">
            <p>${g.nome}</p>
        </div>
    `).join('');
}

function runPC(i) {
    window.location.href = "winlator://";
}

render();
