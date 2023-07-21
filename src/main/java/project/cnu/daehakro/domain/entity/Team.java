package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.cnu.daehakro.domain.enums.MemberSex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long teamId;

    private String applicantId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private MemberSex teamSex;

    @ManyToOne(fetch = FetchType.LAZY)
    private TeamEvent event;

    private String teamName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member",cascade = CascadeType.PERSIST, orphanRemoval = true)
    List<ExcludedDepartment> excludedDepartments = new ArrayList<>();

    @Builder
    public Team(List<Member> members, MemberSex teamSex, TeamEvent event, String teamName, String applicantId) {
        this.members = members;
        this.teamSex = teamSex;
        this.event = event;
        this.teamName = teamName;
        this.applicantId = applicantId;
    }
    public void addExcDepartment(ExcludedDepartment excludedDepartment) {
        this.excludedDepartments.add(excludedDepartment);
    }
}
