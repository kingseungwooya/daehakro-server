package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.entity.Team;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
