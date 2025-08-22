package fr.payetonkawa.clients.service;

import fr.payetonkawa.clients.dto.ClientDto;
import fr.payetonkawa.clients.entity.Client;
import fr.payetonkawa.clients.exception.MissingDataException;
import fr.payetonkawa.clients.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public List<ClientDto> getAllClients() {
        return ClientDto.fromEntities(clientRepository.findAll());
    }

    public Optional<ClientDto> getClientById(Long id) {
        return clientRepository.findById(id)
                .map(ClientDto::from);
    }

    public ClientDto createClient(ClientDto clientDto) {
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setUsername(clientDto.getUsername());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setProfileFirstName(clientDto.getProfileFirstName());
        client.setProfileLastName(clientDto.getProfileLastName());
        client.setPostalCode(clientDto.getPostalCode());
        client.setCity(clientDto.getCity());
        client.setCompanyName(clientDto.getCompanyName());

        Client savedClient = clientRepository.save(client);
        return ClientDto.from(savedClient);
    }

    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new MissingDataException("Client not found with id: " + id));

        if (clientDto.getName() != null) {
            existingClient.setName(clientDto.getName());
        }
        if (clientDto.getUsername() != null) {
            existingClient.setUsername(clientDto.getUsername());
        }
        if (clientDto.getFirstName() != null) {
            existingClient.setFirstName(clientDto.getFirstName());
        }
        if (clientDto.getLastName() != null) {
            existingClient.setLastName(clientDto.getLastName());
        }
        if (clientDto.getProfileFirstName() != null) {
            existingClient.setProfileFirstName(clientDto.getProfileFirstName());
        }
        if (clientDto.getProfileLastName() != null) {
            existingClient.setProfileLastName(clientDto.getProfileLastName());
        }
        if (clientDto.getPostalCode() != null) {
            existingClient.setPostalCode(clientDto.getPostalCode());
        }
        if (clientDto.getCity() != null) {
            existingClient.setCity(clientDto.getCity());
        }
        if (clientDto.getCompanyName() != null) {
            existingClient.setCompanyName(clientDto.getCompanyName());
        }

        Client updatedClient = clientRepository.save(existingClient);
        return ClientDto.from(updatedClient);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

}
