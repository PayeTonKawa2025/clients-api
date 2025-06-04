window.addEventListener('load', function () {

window.filtrerTableau = function(indexColonne) {
    const tableau = document.querySelector("table");
    const input = tableau.querySelectorAll(".filter-row input")[indexColonne - 1];
    const filtre = input.value.toLowerCase();
    const lignes = tableau.tBodies[0].rows;

    for (let ligne of lignes) {
        const cellule = ligne.cells[indexColonne];
        if (cellule) {
            const texte = cellule.textContent.toLowerCase();
            ligne.style.display = texte.includes(filtre) ? "" : "none";
        }
    }
}

        fetch(`/api/clients`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Liste de client introuvable");
                }
                return response.json();
            })
            .then(data => {
                        const tbody = document.querySelector("table tbody");
                        tbody.innerHTML = "";

                        data.forEach(client => {
                            const row = document.createElement("tr");
                            row.setAttribute("data-client-id", client.id);

                            row.innerHTML = `
                                <td hidden>${client.id}</td>
                                <td>${client.companyName}</td>
                                <td>${client.lastName}</td>
                                <td>${client.firstName}</td>
                                <td>${client.postalCode}</td>
                                <td>${client.city}</td>
                                <td>${client.username}</td>
                                <td>
                                <a href=""><i class="bi bi-gear-fill" style="color: var(--chocolat); font-size: 2rem;"></i></a>
                                </td>
                                <td>
                                <i class="bi bi-person-dash"
                                   role="button"
                                   data-bs-toggle="modal"
                                   data-bs-target="#maModale"
                                   data-client-id=${client.id}
                                   style="font-size: 2rem; color: var(--moka); cursor: pointer;"></i></a>
                                </td>
                            `;
                            tbody.appendChild(row);
                        });

                    })
                    .catch(error => {
                        console.error("Erreur lors du chargement des clients :", error);
                    });
    });

const boutonAjoutClient = document.getElementById('boutonAjoutClient');
const modaleAjoutClient = new bootstrap.Modal(document.getElementById('modaleAjoutClient'));

boutonAjoutClient.addEventListener('click', function () {
    modaleAjoutClient.show();
});

const boutonEnregistrer = document.querySelector('#modaleAjoutClient .btn-success');

boutonEnregistrer.addEventListener('click', function () {
    const client = {
        companyName: document.getElementById('companyName').value,
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        postalCode: document.getElementById('postalCode').value,
        city: document.getElementById('city').value,
        profileFirstName: document.getElementById('profileFirstName').value,
        profileLastName: document.getElementById('profileLastName').value,
        username: document.getElementById('username').value
    };

    fetch('/api/clients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(client)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Erreur lors de l'ajout du client");
        }
        return response.json();
    })
    .then(data => {
        // Tu peux recharger la liste ou ajouter la ligne manuellement ici
        bootstrap.Modal.getInstance(document.getElementById('modaleAjoutClient')).hide();
        location.reload(); // Option simple : recharge toute la page
    })
    .catch(error => {
        console.error('Erreur API:', error);
        alert("Erreur lors de l'enregistrement");
    });
});



const maModale = document.getElementById('maModale');
const buttonSuppression = document.getElementById('supprimerClient');

let clientIdASupprimer = null;

maModale.addEventListener('show.bs.modal', function (event) {
    const trigger = event.relatedTarget;
    clientIdASupprimer = trigger.getAttribute('data-client-id');
});

buttonSuppression.addEventListener("click", (event) => {
    if (!clientIdASupprimer) return;

    fetch(`/api/clients/${clientIdASupprimer}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Échec de la suppression");
        }

        const modalInstance = bootstrap.Modal.getInstance(maModale);
        modalInstance.hide();

        const ligne = document.querySelector(`tr[data-client-id="${clientIdASupprimer}"]`);
        if (ligne) {
            ligne.remove();
        }

    })
    .catch(error => {
        console.error("Erreur lors de la suppression :", error);
    });
});


