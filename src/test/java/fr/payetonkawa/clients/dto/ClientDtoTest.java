package fr.payetonkawa.clients.dto;

import fr.payetonkawa.clients.entity.Client;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDtoTest {

    @Test
    void from_ShouldConvertClientToClientDto() {
        // Arrange
        Client client = new Client(1L, "Test Name", "testuser", "Test", "User", "ProfileFirst", "ProfileLast", "12345", "City", "Company");

        // Act
        ClientDto clientDto = ClientDto.from(client);

        // Assert
        assertNotNull(clientDto);
        assertEquals(client.getId(), clientDto.getId());
        assertEquals(client.getName(), clientDto.getName());
        assertEquals(client.getUsername(), clientDto.getUsername());
        assertEquals(client.getFirstName(), clientDto.getFirstName());
        assertEquals(client.getLastName(), clientDto.getLastName());
        assertEquals(client.getProfileFirstName(), clientDto.getProfileFirstName());
        assertEquals(client.getProfileLastName(), clientDto.getProfileLastName());
        assertEquals(client.getPostalCode(), clientDto.getPostalCode());
        assertEquals(client.getCity(), clientDto.getCity());
        assertEquals(client.getCompanyName(), clientDto.getCompanyName());
    }

    @Test
    void fromEntities_ShouldConvertClientListToClientDtoList() {
        // Arrange
        Client client1 = new Client(1L, "Test Name 1", "testuser1", "Test1", "User1", "ProfileFirst1", "ProfileLast1", "12345", "City1", "Company1");
        Client client2 = new Client(2L, "Test Name 2", "testuser2", "Test2", "User2", "ProfileFirst2", "ProfileLast2", "67890", "City2", "Company2");
        List<Client> clients = List.of(client1, client2);

        // Act
        List<ClientDto> clientDtos = ClientDto.fromEntities(clients);

        // Assert
        assertNotNull(clientDtos);
        assertEquals(2, clientDtos.size());

        ClientDto clientDto1 = clientDtos.get(0);
        assertEquals(client1.getId(), clientDto1.getId());
        assertEquals(client1.getName(), clientDto1.getName());

        ClientDto clientDto2 = clientDtos.get(1);
        assertEquals(client2.getId(), clientDto2.getId());
        assertEquals(client2.getName(), clientDto2.getName());
    }
}