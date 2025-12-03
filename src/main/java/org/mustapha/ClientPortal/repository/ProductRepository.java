package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
