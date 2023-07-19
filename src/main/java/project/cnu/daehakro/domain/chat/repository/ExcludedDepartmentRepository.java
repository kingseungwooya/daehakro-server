package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.ExcludedDepartment;

import java.util.List;

@Repository
public interface ExcludedDepartmentRepository extends JpaRepository<ExcludedDepartment, Long> {
    List<ExcludedDepartment> findAllByEventId(Long eventId);
    void deleteAllByEventId(Long eventId);
}
