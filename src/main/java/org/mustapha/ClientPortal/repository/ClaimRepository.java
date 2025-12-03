package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    Page<Claim> findByClient_Username(String username, Pageable pageable);
    List<Claim> findByClientId(Long clientId);
}
