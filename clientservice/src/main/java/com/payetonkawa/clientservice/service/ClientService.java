package com.payetonkawa.clientservice.service;

import com.payetonkawa.clientservice.model.Client;
import com.payetonkawa.clientservice.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getAll() {
        return repository.findAll();
    }

    public Optional<Client> getById(Long id) {
        return repository.findById(id);
    }

    public Client save(Client client) {
        return repository.save(client);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
