package project.cnu.daehakro.domain.chat.service.matcher;

import lombok.Getter;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.entity.Team;
import project.cnu.daehakro.domain.enums.EventType;

import java.util.ArrayList;
import java.util.List;

import static project.cnu.daehakro.domain.chat.service.matcher.Randoms.shuffle;

@Getter
public class TeamEventMatcher {

    private static final int START_NUMBER = 0;
    private List<List<Team>> selectedTeams;


    public TeamEventMatcher(final List<Team> menTeams, final List<Team> womanTeams, EventType eventType) {
        this.selectedTeams = makeTeam(menTeams, womanTeams, eventType);
    }

     public List<List<Team>> makeTeam(
             final List<Team> menTeams,
             final List<Team> womanTeams,
             final EventType eventType
     ) {
         int maxNum = Math.min(menTeams.size(), womanTeams.size());
         List<Team> shuffledMens = shuffle(menTeams);
         List<Team> shuffledWomans = shuffle(womanTeams);

         int teamSize = eventType.getLimitOfEachSex();
         int teamCount = maxNum % teamSize;
         List<List<Team>> teams = new ArrayList<>(teamCount);
         for (int i = 0; i < (teamCount * teamSize); i += teamSize) {
             List<Team> team = new ArrayList<>();
             for (int j = 0; j < teamSize; j++) {
                 team.add(shuffledMens.get(i + j));
                 team.add(shuffledWomans.get(i + j));
             }
             teams.add(team);
         }
         return teams;
     }


}
