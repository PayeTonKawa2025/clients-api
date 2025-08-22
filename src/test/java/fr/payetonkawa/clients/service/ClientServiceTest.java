package fr.payetonkawa.clients.service;

import fr.payetonkawa.clients.dto.ClientDto;
import fr.payetonkawa.clients.entity.Client;
import fr.payetonkawa.clients.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllClients_ShouldReturnClientDtoList() {
        // Arrange
        Client client = new Client(1L, "Test Name", "testuser", "Test", "User", "ProfileFirst", "ProfileLast", "12345", "City", "Company");
        when(clientRepository.findAll()).thenReturn(List.of(client));

        // Act
        List<ClientDto> result = clientService.getAllClients();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Name", result.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById_ShouldReturnClientDto_WhenClientExists() {
        // Arrange
        Client client = new Client(1L, "Test Name", "testuser", "Test", "User", "ProfileFirst", "ProfileLast", "12345", "City", "Company");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        // Act
        Optional<ClientDto> result = clientService.getClientById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Name", result.get().getName());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void getClientById_ShouldReturnEmptyOptional_WhenClientDoesNotExist() {
        // Arrange
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<ClientDto> result = clientService.getClientById(1L);

        // Assert
        assertFalse(result.isPresent());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void deleteClient_ShouldCallRepositoryDeleteById() {
        // Act
        clientService.deleteClient(1L);

        // Assert
        verify(clientRepository, times(1)).deleteById(1L);
    }
}