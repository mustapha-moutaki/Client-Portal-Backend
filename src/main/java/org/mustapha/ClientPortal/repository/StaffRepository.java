package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findBySupervisorId(Long supervisorId);

    Optional<Staff> findByEmail(String email);
}
