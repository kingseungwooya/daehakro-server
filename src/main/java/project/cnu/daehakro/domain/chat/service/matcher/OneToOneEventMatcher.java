package project.cnu.daehakro.domain.chat.service.matcher;

import lombok.Getter;
import project.cnu.daehakro.domain.entity.Member;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 알고리즘 고도화 필요..
 */
@Getter
public class OneToOneEventMatcher{

   // private static final int START_NUMBER = 0;
    private List<List<Member>> selectedCouples;

    public OneToOneEventMatcher(final List<Member> mens, final List<Member> women) {
        this.selectedCouples = makeCouple(mens, women);
    }

    // private List<List<Member>> makeCouple(
    //         final List<Member> mens,
    //         final List<Member> womens
    // ) {
    //     int maxNum = Math.min(mens.size(), womens.size());
    //     List<Integer> selectedMenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, mens.size() - 1, maxNum);
    //     List<Integer> selectedWomenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, womens.size() - 1, maxNum);
    //     List<List<Member>> selectedCouples = new ArrayList<>();
//
//
    //     for (int i = 0; i < maxNum; i++) {
//
    //         Member men = mens.get(selectedMenIndex.get(i));
    //         Member women = womens.get(selectedWomenIndex.get(i));
    //         // 같은 과에 속하는지 확인
    //         if (men.getDepartment().equals(women.getDepartment())) {
    //             // 같은 과라면 다시 랜덤 매칭 수행
    //             return makeCouple(mens, womens);
    //         }
    //         selectedCouples.add(List.of(men, women));
    //     }
    //     return selectedCouples;
    // }

    private List<List<Member>> makeCouple(
            final List<Member> men,
            final List<Member> women
    ) {

        return men.stream()
                .flatMap(man -> women.stream()
                        .filter(woman -> !man.getDepartment().equals(woman.getDepartment()))
                        .findFirst()
                        .map(woman -> List.of(man, woman))
                        .stream())
                .collect(Collectors.toList());
    }





}
