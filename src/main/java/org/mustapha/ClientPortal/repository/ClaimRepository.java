package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByClientId(Long clientId);
}
