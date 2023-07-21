package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.enums.Department;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_name")
    private String memberName;

    private int age;

    @Enumerated(EnumType.STRING)
    private MemberSex sex;

    private int coin;

    private int teamCoin;

    @Column(name = "certify_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isCertify;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private UnivInfo univInfo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    List<EventLog> eventLogs = new ArrayList<>();
    // ExcludedDepartment 객체는 event 종료시 모두 사라진다. 그에 맞게 Persist로 변경하여 save 시 영속성 보장을 해주고 orphanRemoval을 통해 delete시 같이 제거되도록 한다
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member",cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<ExcludedDepartment> excludedDepartments = new ArrayList<>();


    @Builder
    public Member(String memberId, String memberName, int age, MemberSex sex, ChatRoom chatRoom, UnivInfo univInfo, Department department) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.age = age;
        this.sex = sex;
        this.coin = 1;
        this.isCertify = false;
        this.chatRoom = chatRoom;
        this.univInfo = univInfo;
        this.teamCoin = 1;
        this.department = department;
        // this.haveEvent = false;
    }

    @Enumerated(EnumType.STRING)
    private Department department;


    public void applyEvent(EventLog eventLog) {
        this.eventLogs.add(eventLog);
    }

    public void useTeamCoin() {
        this.teamCoin = teamCoin - 1;
    }

    public void useCoin() {
        this.coin = coin - 1;
    }

    public boolean haveCoin() {
        if (this.coin > 0) {
            return true;
        }
        return false;
    }
    public boolean haveTeamCoin() {
        if (this.teamCoin > 0) {
            return true;
        }
        return false;
    }

    public void addExcDepartment(ExcludedDepartment excludedDepartment) {
        this.excludedDepartments.add(excludedDepartment);
    }

}
