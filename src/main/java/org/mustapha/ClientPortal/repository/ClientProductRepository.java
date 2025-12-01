package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.ClientProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientProductRepository extends JpaRepository<ClientProduct, Long> {
    List<ClientProduct> findByClientId(Long clientId);
    List<ClientProduct> findByProductId(Long productId);
}
