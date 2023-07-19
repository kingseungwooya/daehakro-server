package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import project.cnu.daehakro.domain.enums.Department;

import javax.persistence.*;

/**
 * 이 객체는 이벤트가 종료될때마다 다 없어져야 한다.
 */
@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "excluded_department")
public class ExcludedDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exc_id")
    private Long excId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "event_id")
    private Long eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "exc_department")
    private Department excDepartment;

    @Builder
    public ExcludedDepartment(Member member, Long eventId, Department excDepartment) {
        this.member = member;
        this.eventId = eventId;
        this.excDepartment = excDepartment;
    }
}
