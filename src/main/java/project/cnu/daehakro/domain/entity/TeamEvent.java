package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import project.cnu.daehakro.domain.enums.EventType;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "team_event")
public class TeamEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    private String eventName;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false)
    private Timestamp createAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    private int maxApply;

    @Column(name = "open_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isOpen;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private List<Team> manTeams = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event", cascade = CascadeType.ALL)
    private List<Team> womenTeams = new ArrayList<>();

    @Builder
    public TeamEvent(String eventName, LocalDate startDate, LocalDate endDate, int maxApply, EventType eventType) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxApply = maxApply;
        // 생성 즉시 open 할 것인가?
        this.isOpen = true;
        this.eventType = eventType;
    }

    public void close() {
        this.isOpen = false;
    }

    public void applyTeam(Team team) {
        if (team.getTeamSex().equals(MemberSex.MAN)) {
            System.out.println("남자 신청이요~~~~~~~");
            manTeams.add(team);
        } else {
            womenTeams.add(team);
        }
    }

    public boolean isFull(MemberSex sex) {
        if (sex.equals(MemberSex.MAN)) {
            return manTeams.size() == maxApply;
        }
        if (sex.equals(MemberSex.WOMAN)) {
            return womenTeams.size() == maxApply;
        }
        return false;
    }
}
