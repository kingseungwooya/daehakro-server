package project.cnu.daehakro.domain.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.dto.EventResDto;
import project.cnu.daehakro.domain.chat.dto.UnivInfoDto;
import project.cnu.daehakro.domain.chat.repository.ChatRoomRepository;
import project.cnu.daehakro.domain.chat.repository.EventRepository;
import project.cnu.daehakro.domain.chat.repository.MemberRepository;
import project.cnu.daehakro.domain.chat.repository.UnivRepository;
import project.cnu.daehakro.domain.entity.ChatRoom;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.entity.Member;
import project.cnu.daehakro.domain.entity.UnivInfo;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UnivRepository univRepository;
    private final EventRepository eventRepository;


    /**
     * random sorting 조건은? 대학별? 등등 추후 구현해야할듯?
     */
    @Override
    public void randomMatch(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomApiException(ResponseEnum.EVENT_NOT_EXIST)
        );
        if (!isEnd(event.getEndDate())) {
            throw new CustomApiException(ResponseEnum.EVENT_NOT_ENDED);
        }
        // refactoring 필요 임시코드
        List<Member> mens = event.getMembersOfMan();
        List<Member> womens = event.getMembersOfWomen();
        int numOfCreate = Math.min(mens.size(), womens.size());
        Collections.shuffle(mens);
        Collections.shuffle(womens);
        for (int i = 0; i < numOfCreate; i++) {
            ChatRoom room = ChatRoom.builder()
                    .title("좋은 시간, 좋은 사람, 좋은 대화")
                    .build();
            room.join(mens.get(i));
            room.join(womens.get(i));
            chatRoomRepository.save(room);
        }
        if (mens.size() > womens.size()) {
            // 쿠폰 보상
        }
        if (womens.size() < mens.size()) {
            // 쿠폰 보상
        }

    }

    private boolean isEnd(LocalDate endDate) {
        // 마감일짜보다 일찍 matching을 할 시
        if (endDate.compareTo(LocalDate.now()) <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public void closeEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new CustomApiException(ResponseEnum.EVENT_NOT_EXIST)
        );
        event.close();
    }


    /**
     * 관리자 권한으로 이벤트 생성
     */
    @Override
    public void createEvent(EventDto eventDto) {
        UnivInfo univInfo = univRepository.findById(eventDto.getUnivId()).orElseThrow(
                () -> new CustomApiException(ResponseEnum.UNIV_NOT_EXIST)
        );
        Event event = Event.builder()
                .eventName(eventDto.getEventName())
                .maxApply(eventDto.getMaxApply())
                .startDate(eventDto.getStartDate())
                .endDate(eventDto.getEndDate())
                .univInfo(univInfo)
                .build();
        eventRepository.save(event);
    }

    /**
     * 생성되었던 event들을 가져온다. 생성 순으로
     */
    @Override
    public List<EventResDto> getAllEvents() {
        List<Event> events = eventRepository.findAllByOrderByCreateAtDesc();

        return events.stream()
                .map(e ->
                        EventResDto.builder()
                                .eventId(e.getEventId())
                                .maxApply(e.getMaxApply())
                                .startDate(e.getStartDate())
                                .endDate(e.getEndDate())
                                .createAt(e.getCreateAt())
                                .open(e.isOpen())
                                .manApply(e.getMembersOfMan().size())
                                .womanApply(e.getMembersOfWomen().size())
                                .build())
                .collect(Collectors.toList());
    }


    private void createChatRoom() {

    }
}
