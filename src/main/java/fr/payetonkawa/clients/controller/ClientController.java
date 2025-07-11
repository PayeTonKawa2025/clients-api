package fr.payetonkawa.clients.controller;

import fr.payetonkawa.clients.dto.ClientDto;
import fr.payetonkawa.clients.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public List<ClientDto> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{id}")
    public ClientDto getClientById(Long id) {
        return clientService.getClientById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
    }

    @PostMapping
    public ClientDto createClient(ClientDto clientDto) {
        return clientService.createClient(clientDto);
    }

    @PatchMapping("/{id}")
    public ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto) {
        return clientService.updateClient(id, clientDto);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

}
