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

    public EventMatcher(final List<T> mens, final List<T> womens) {
        this.selectedCouples = makeCouple(mens, womens);
    }

    private List<List<T>> makeCouple(
            final List<T> mens,
            final List<T> womens
    ) {
        int maxNum = Math.min(mens.size(), womens.size());
        List<Integer> selectedMenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, mens.size() - 1, maxNum);
        List<Integer> selectedWomenIndex = Randoms.pickUniqueNumbersInRange(START_NUMBER, womens.size() - 1, maxNum);
        List<T> nonSelectedMembers = new ArrayList<>();

        List<List<T>> selectedCouples = new ArrayList<>();

        for (int i = 0; i < maxNum; i++) {

            T men = mens.get(selectedMenIndex.get(i));
            T women = womens.get(selectedWomenIndex.get(i));
            selectedCouples.add(List.of(men, women));
        }
        return selectedCouples;
    }


}
