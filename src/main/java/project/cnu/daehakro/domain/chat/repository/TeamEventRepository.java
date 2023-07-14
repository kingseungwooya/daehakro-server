package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.entity.TeamEvent;

import java.util.List;

@Repository
public interface TeamEventRepository extends JpaRepository<TeamEvent, Long> {
    List<Event> findAllByOrderByCreateAtDesc();
}
