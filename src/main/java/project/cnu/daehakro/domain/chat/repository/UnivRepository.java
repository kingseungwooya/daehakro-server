package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.UnivInfo;

import java.util.List;

@Repository
public interface UnivRepository extends JpaRepository<UnivInfo, Long> {
    List<UnivInfo> findAll();
}

