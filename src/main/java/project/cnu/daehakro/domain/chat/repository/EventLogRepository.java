package project.cnu.daehakro.domain.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.cnu.daehakro.domain.entity.EventLog;
import project.cnu.daehakro.domain.entity.Member;

import java.util.List;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    boolean existsByEventIdAndMember(Long eventId, Member member);
    boolean existsByMemberAndClose(Member member, boolean isClose);

    EventLog findByMemberAndEventId(Member member, Long eventId);

    List<EventLog> findByEventId(Long eventId);
}
