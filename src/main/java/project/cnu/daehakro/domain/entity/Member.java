package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.persistence.*;

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
    // 현재 이벤트에 참여중인 멤버인지.. 초기값은 항상 false여야한다.
    @Column(name = "match_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isMatch;

    @Column(name = "certify_flag", nullable = false, columnDefinition = "TINYINT", length = 1)
    private boolean isCertify;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "univ_id")
    private UnivInfo univInfo;

    @Builder
    public Member(String memberId, String memberName, int age, MemberSex sex, int coin, boolean isCertify, ChatRoom chatRoom, UnivInfo univInfo) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.age = age;
        this.sex = sex;
        this.coin = coin;
        this.isMatch = false;
        this.isCertify = isCertify;
        this.chatRoom = chatRoom;
        this.univInfo = univInfo;
    }

    public void match() {
        this.isMatch = true;
        useCoin();
    }

    public void useCoin() {
        this.coin = coin - 1;
    }

}
