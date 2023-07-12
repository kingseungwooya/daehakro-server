package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import project.cnu.daehakro.domain.chat.repository.MemberRepository;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Event {

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
    // 이벤트 홍보 페이지도 하나씩 넣으면 좋을듯? 사진느낌으로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private UnivInfo univInfo;

    // 하나의 event 당 신청자들의 정보를 갖고있는다.
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventId")
    private List<Member> membersOfWomen = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eventId")
    private List<Member> membersOfMan = new ArrayList<>();

    @Builder
    public Event(String eventName, LocalDate startDate, LocalDate endDate, int maxApply, UnivInfo univInfo) {
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxApply = maxApply;
        // 생성 즉시 open 할 것인가?
        this.isOpen = true;
        this.univInfo = univInfo;
    }

    public void close() {
        this.isOpen = false;
    }

    public void apply(Member member) {
        if (member.getSex().equals(MemberSex.MAN)) {
            membersOfMan.add(member);
        } else {
            membersOfWomen.add(member);
        }
    }
}
