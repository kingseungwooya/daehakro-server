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
public class EventMatcher <T>{

    private static final int START_NUMBER = 0;
    private List<List<T>> selectedCouples;

    public EventMatcher(final List<T> men, final List<T> women) {
        this.selectedCouples = makeCouple(men, women);
    }

    private List<List<T>> makeCouple(
            final List<T> men,
            final List<T> women
    ) {
        int maxNum = Math.min(men.size(), women.size());
        List<Integer> selectedMenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, men.size() - 1, maxNum);
        List<Integer> selectedWomenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, women.size() - 1, maxNum);

        List<List<T>> selectedCouples = new ArrayList<>();

        for (int i = 0; i < maxNum; i++) {

            T man = men.get(selectedMenIndex.get(i));
            T woman = women.get(selectedWomenIndex.get(i));
            selectedCouples.add(List.of(man, woman));
        }
        return selectedCouples;
    }


}
