package project.cnu.daehakro.domain.chat.service.matcher;

import lombok.Getter;
import project.cnu.daehakro.domain.entity.Member;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 알고리즘 고도화 필요..
 */
@Getter
public class OneToOneEventMatcher{

    private static final int START_NUMBER = 0;
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
    //     for (int i = 0; i < maxNum; i++) {
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
    // private List<List<Member>> makeCouples(
    //         final List<Member> mens,
    //         final List<Member> womens
    // ) {
    //     int maxNum = Math.min(mens.size(), womens.size());
    //     List<Integer> selectedMenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, mens.size() - 1, maxNum);
    //     List<Integer> selectedWomenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, womens.size() - 1, maxNum);
    //     List<List<Member>> selectedCouples = new ArrayList<>();
    //     for (int i = 0; i < maxNum; i++) {
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
    // man에서 한명뽑는다 Woman 에서 한명 뽑는다 만약 조건에 부합한다면 둘의 인덱스를 제거한다.
    // 만약에 조건에 부합하지 않는다면 woman 에서 다른 index를 뽑는다. 이렇게 한쪽 인덱스가 다 없어질때까지 진행
    private List<List<Member>> makeCouple(
            final List<Member> mens,
            final List<Member> women
    ) {
        List<Member> availableMens = new ArrayList<>(mens);
        List<Member> availableWomen = new ArrayList<>(women);
        List<List<Member>> selectedCouples = new ArrayList<>();

        while (!availableMens.isEmpty() || !availableWomen.isEmpty()) {
            // 남자 리스트에서 랜덤하게 한 명 뽑기
            int randomManIndex = new Random().nextInt(availableMens.size());
            Member man = availableMens.get(randomManIndex);

            // 여자 리스트에서 랜덤하게 한 명 뽑기
            int randomWomanIndex = new Random().nextInt(availableWomen.size());
            Member woman = availableWomen.get(randomWomanIndex);

            // 같은 과에 속하는지 확인
            if (man.getDepartment().equals(woman.getDepartment())) {
                // 모든 학생들이 같은 과에 속한 경우 루프를 종료
                boolean allInSameDepartment = availableMens.stream()
                        .allMatch(m -> m.getDepartment().equals(man.getDepartment()))
                        && availableWomen.stream()
                        .allMatch(w -> w.getDepartment().equals(woman.getDepartment()));

                if (allInSameDepartment) {
                    break;
                }
                // 같은 과라면 다시 랜덤 매칭 수행
                continue;
            }

            // 매칭된 커플을 결과 리스트에 추가하고, 뽑힌 인덱스는 제거
            selectedCouples.add(List.of(man, woman));
            availableMens.remove(randomManIndex);
            availableWomen.remove(randomWomanIndex);
        }

        return selectedCouples;
    }
    //private List<List<Member>> makeCouple(
    //        final List<Member> men,
    //        final List<Member> women
    //) {
    //    List<Member> shuffledMen = Randoms.shuffle(men);
    //    List<Member> shuffledWomen = Randoms.shuffle(women);
    //    int maxIter = Math.min(shuffledMen.size(), shuffledWomen.size());
    //    for(int i = 0; i < maxIter - 1; i ++) {
    //
    //    }
    //    shuffledMen.remove()
    //
    //    return men.stream()
    //            .flatMap(man -> women.stream()
    //                    .filter(woman -> !man.getDepartment().equals(woman.getDepartment()))
    //                    .findFirst()
    //                    .map(woman -> List.of(man, woman))
    //                    .stream())
    //            .collect(Collectors.toList());
    //}
//
//



}
