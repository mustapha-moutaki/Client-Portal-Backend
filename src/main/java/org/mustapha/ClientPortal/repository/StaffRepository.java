package org.mustapha.ClientPortal.repository;

import org.mustapha.ClientPortal.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findBySupervisorId(Long supervisorId);
}
