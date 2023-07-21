package project.cnu.daehakro.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@RequiredArgsConstructor
@Table(name = "event_log")
@Getter
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "close_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isClose;
    @Column(name = "match_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isMatch;

    @Builder
    public EventLog(Long eventId, Member member) {
        this.eventId = eventId;
        this.member = member;
        this.isClose = false;
        this.isMatch = false;
    }

    public void match() {
        this.isMatch = true;
    }

    public void close() {
        this.isClose = true;
    }
}
