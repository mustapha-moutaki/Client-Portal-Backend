package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByEmail(String email);
}
