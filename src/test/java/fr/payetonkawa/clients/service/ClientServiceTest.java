package fr.payetonkawa.clients.service;

import fr.payetonkawa.clients.dto.ClientDto;
import fr.payetonkawa.clients.entity.Client;
import fr.payetonkawa.clients.exception.MissingDataException;
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

    @Test
    void createClient_ShouldCreateAndReturnClientDto() {
        // Arrange
        ClientDto clientDto = new ClientDto(null, "New Name", "newuser", "New", "User", "ProfileFirst", "ProfileLast", "12345", "City", "Company");
        Client savedClient = new Client(1L, "New Name", "newuser", "New", "User", "ProfileFirst", "ProfileLast", "12345", "City", "Company");

        when(clientRepository.save(any(Client.class))).thenReturn(savedClient);

        // Act
        ClientDto result = clientService.createClient(clientDto);

        // Assert
        assertNotNull(result);
        assertEquals("New Name", result.getName());
        assertEquals("newuser", result.getUsername());
        assertEquals("New", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("ProfileFirst", result.getProfileFirstName());
        assertEquals("ProfileLast", result.getProfileLastName());
        assertEquals("12345", result.getPostalCode());
        assertEquals("City", result.getCity());
        assertEquals("Company", result.getCompanyName());
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void updateClient_ShouldUpdateAndReturnClientDto_WhenClientExists() {
        // Arrange
        Client existingClient = new Client(1L, "Old Name", "olduser", "Old", "User", "OldProfileFirst", "OldProfileLast", "54321", "OldCity", "OldCompany");
        Client updatedClient = new Client(1L, "Updated Name", "updateduser", "Updated", "User", "UpdatedProfileFirst", "UpdatedProfileLast", "12345", "UpdatedCity", "UpdatedCompany");
        ClientDto updateDto = new ClientDto(1L, "Updated Name", "updateduser", "Updated", "User", "UpdatedProfileFirst", "UpdatedProfileLast", "12345", "UpdatedCity", "UpdatedCompany");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        // Act
        ClientDto result = clientService.updateClient(1L, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updateduser", result.getUsername());
        assertEquals("Updated", result.getFirstName());
        assertEquals("User", result.getLastName());
        assertEquals("UpdatedProfileFirst", result.getProfileFirstName());
        assertEquals("UpdatedProfileLast", result.getProfileLastName());
        assertEquals("12345", result.getPostalCode());
        assertEquals("UpdatedCity", result.getCity());
        assertEquals("UpdatedCompany", result.getCompanyName());
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void updateClient_ShouldThrowMissingDataException_WhenClientDoesNotExist() {
        // Arrange
        ClientDto updateDto = new ClientDto(1L, "Updated Name", "updateduser", "Updated", "User", "UpdatedProfileFirst", "UpdatedProfileLast", "12345", "UpdatedCity", "UpdatedCompany");
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(MissingDataException.class, () -> clientService.updateClient(1L, updateDto));
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    void updateClient_ShouldUpdateOnlyNonNullFields() {
        // Arrange
        Client existingClient = new Client(1L, "Old Name", "olduser", "Old", "User", "OldProfileFirst", "OldProfileLast", "54321", "OldCity", "OldCompany");
        ClientDto partialUpdateDto = new ClientDto(1L, "Updated Name", null, null, null, null, null, null, null, null);

        when(clientRepository.findById(1L)).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(any(Client.class))).thenReturn(existingClient);

        // Act
        ClientDto result = clientService.updateClient(1L, partialUpdateDto);

        // Assert
        assertNotNull(result);
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}