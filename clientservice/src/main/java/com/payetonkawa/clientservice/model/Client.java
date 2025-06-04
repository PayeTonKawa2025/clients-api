package com.payetonkawa.clientservice.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String username;
    @CsvBindByName
    private String firstName;
    @CsvBindByName
    private String lastName;
    @CsvBindByName
    private String profileFirstName;
    @CsvBindByName
    private String profileLastName;
    @CsvBindByName
    private String postalCode;
    @CsvBindByName
    private String city;
    @CsvBindByName
    private String companyName;
}
