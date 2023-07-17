package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.entity.Team;
import project.cnu.daehakro.domain.entity.TeamEvent;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    void deleteAllByEvent(TeamEvent event);
}
