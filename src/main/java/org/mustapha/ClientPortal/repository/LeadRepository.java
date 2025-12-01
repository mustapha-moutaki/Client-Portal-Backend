package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByAssignedOperatorId(Long operatorId);
}
