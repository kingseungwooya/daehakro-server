package project.cnu.daehakro.domain.chat.service.matcher;

import lombok.Getter;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.enums.EventType;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static project.cnu.daehakro.domain.chat.service.matcher.Randoms.shuffle;

@Getter
public class EventMatcher {

    private static final int START_NUMBER = 0;
    private List<List<Member>> selectedMembers;
    private List<Member> nonSelectedMembers;

    public EventMatcher(final List<Member> mens, final List<Member> womens) {
        this.selectedMembers = makeCouple(mens, womens);
    }

    // private List<List<Member>> selectLogic(List<Member> mens, List<Member> womens, EventType eventType) {
    //     if (eventType.getLimitOfEachSex() == 1) {
    //         return makeCouple(mens, womens);
    //     }
    //     return makeTeam(mens, womens, eventType);
    // }

    public List<List<Member>> makeCouple(
            final List<Member> mens,
            final List<Member> womens
    ) {
        int maxNum = Math.min(mens.size(), womens.size());
        List<Integer> selectedMenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, mens.size() - 1, maxNum);
        List<Integer> selectedWomenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, womens.size() - 1, maxNum);
        List<List<Member>> nonSelectedMembers = new ArrayList<>();

        List<List<Member>> selectedCouples = new ArrayList<>();

        for (int i = 0; i < maxNum; i++) {

            Member men = mens.get(selectedMenIndex.get(i));
            Member women = womens.get(selectedWomenIndex.get(i));
            selectedCouples.add(List.of(men, women));
        }
        return selectedCouples;
    }

    // 2대2 or ... 내가 랜덤으로 해주는게 아니라 친구랑 신청하는거임...
    // public List<List<Member>> makeTeam(
    //         final List<Member> mens,
    //         final List<Member> womens,
    //         final EventType eventType
    // ) {
    //     int maxNum = Math.min(mens.size(), womens.size());
    //     List<Member> shuffledMens = shuffle(mens);
    //     List<Member> shuffledWomens = shuffle(womens);

    //     int teamSize = eventType.getLimitOfEachSex();
    //     int teamCount = maxNum % teamSize;
    //     List<List<Member>> teams = new ArrayList<>(teamCount);
    //     for (int i = 0; i < (teamCount * teamSize); i += teamSize) {
    //         List<Member> team = new ArrayList<>();
    //         for (int j = 0; j < teamSize; j++) {
    //             team.add(shuffledMens.get(i + j));
    //             team.add(shuffledWomens.get(i + j));
    //         }
    //         teams.add(team);
    //     }
    //     return teams;
    // }


}
