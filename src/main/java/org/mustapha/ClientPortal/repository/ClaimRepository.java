package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Claim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {



    Page<Claim> findByClientEmail(String email, Pageable pageable);

    Page<Claim> findByAssignedStaffEmail(String email, Pageable pageable);

    List<Claim> findByClientId(Long clientId);
}