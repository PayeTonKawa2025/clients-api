package fr.payetonkawa.clients.dto;

import fr.payetonkawa.clients.entity.Client;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private Long id;
    private String name;
    private String username;
    private String firstName;
    private String lastName;
    private String profileFirstName;
    private String profileLastName;
    private String postalCode;
    private String city;
    private String companyName;

    public static ClientDto from(Client client) {
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setUsername(client.getUsername());
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setProfileFirstName(client.getProfileFirstName());
        dto.setProfileLastName(client.getProfileLastName());
        dto.setPostalCode(client.getPostalCode());
        dto.setCity(client.getCity());
        dto.setCompanyName(client.getCompanyName());
        return dto;
    }

    public static List<ClientDto> fromEntities(List<Client> clients) {
        return clients.stream()
                .map(ClientDto::from)
                .toList();
    }

}
