package fr.payetonkawa.clients.service;

import fr.payetonkawa.clients.dto.ClientDto;
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
        return null;
    }

    public ClientDto updateClient(Long id, ClientDto clientDto) {
        return null;
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

}
