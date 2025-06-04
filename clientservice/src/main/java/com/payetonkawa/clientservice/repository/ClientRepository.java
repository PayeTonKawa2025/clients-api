package com.payetonkawa.clientservice.repository;

import com.payetonkawa.clientservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
