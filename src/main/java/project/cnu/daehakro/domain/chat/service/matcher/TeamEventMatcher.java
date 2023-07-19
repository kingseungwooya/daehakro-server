package project.cnu.daehakro.domain.chat.service.matcher;

import lombok.Getter;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.entity.Team;
import project.cnu.daehakro.domain.enums.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static project.cnu.daehakro.domain.chat.service.matcher.Randoms.shuffle;

@Getter
public class TeamEventMatcher {

    private static final int START_NUMBER = 0;
    private static final int MAX_LOOP_COUNT = 10000;
    private List<List<Team>> selectedTeams;


    public TeamEventMatcher(final List<Team> menTeams, final List<Team> womanTeams) {
        this.selectedTeams = makeTeam(menTeams, womanTeams);
    }

    public List<List<Team>> makeTeam(
            final List<Team> menTeams,
            final List<Team> womanTeams
    ) {
        int loopCount = 0;
        List<Team> availableMenTeams = new ArrayList<>(menTeams);
        List<Team> availableWomenTeams = new ArrayList<>(womanTeams);
        List<List<Team>> selectedCouples = new ArrayList<>();

        while (!availableMenTeams.isEmpty() || !availableWomenTeams.isEmpty()) {
            loopCount++;

            // 남자 리스트에서 랜덤하게 한 팀 뽑기
            int randomManIndex = new Random().nextInt(availableMenTeams.size());
            Team manTeam = availableMenTeams.get(randomManIndex);
            Member applicantOfMan = manTeam.getMembers().stream().
                    filter(m -> m.getMemberId() == manTeam.getApplicantId())
                    .findFirst().get();

            // 여자 리스트에서 랜덤하게 한 팀 뽑기
            int randomWomanIndex = new Random().nextInt(availableWomenTeams.size());
            Team womanTeam = availableWomenTeams.get(randomWomanIndex);
            Member applicantOfWoman = womanTeam.getMembers().stream().
                    filter(m -> m.getMemberId() == womanTeam.getApplicantId())
                    .findFirst().get();
            if (
                    applicantOfMan.getExcludedDepartments().stream()
                            .anyMatch(
                                    d -> d.getExcDepartment().equals(applicantOfWoman.getDepartment())
                            ) ||
                            applicantOfWoman.getExcludedDepartments().stream()
                            .anyMatch(
                                    d -> d.getExcDepartment().equals(applicantOfMan.getDepartment())
                            )
            ) {
                // 무한루프 방지용 추후 수정 필요
                if (loopCount > MAX_LOOP_COUNT) {
                    break;
                }

                // 같은 과라면 다시 랜덤 매칭 수행
                continue;
            }

            // 매칭된 커플을 결과 리스트에 추가하고, 뽑힌 인덱스는 제거
            selectedCouples.add(List.of(manTeam, womanTeam));
            availableMenTeams.remove(randomManIndex);
            availableWomenTeams.remove(randomWomanIndex);
        }


        return selectedCouples;
    }


}
