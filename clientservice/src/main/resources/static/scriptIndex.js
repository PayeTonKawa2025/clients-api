document.getElementById('client-form').addEventListener('submit', function(event) {
        event.preventDefault();

        const clientId = document.getElementById('client-id').value;
        const nameField = document.getElementById('client-name');
        const companyField = document.getElementById('company');
        const message = document.getElementById('result-message');

        fetch(`/api/clients/${clientId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Client introuvable");
                }
                return response.json();
            })
            .then(data => {
                nameField.value = data.name;
                companyField.value = data.companyName;
                message.textContent = "✅ Client trouvé";
                message.style.color = "green";
            })
            .catch(error => {
                nameField.value = '';
                companyField.value = '';
                message.textContent = "❌ " + error.message;
                message.style.color = "red";
            });
    });