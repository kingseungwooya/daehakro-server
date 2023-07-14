package project.cnu.daehakro.domain.chat.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import project.cnu.daehakro.domain.chat.dto.EventDto;
import project.cnu.daehakro.domain.chat.dto.EventResDto;
import project.cnu.daehakro.domain.chat.dto.UnivInfoDto;
import project.cnu.daehakro.domain.chat.repository.*;
import project.cnu.daehakro.domain.chat.service.matcher.EventMatcher;
import project.cnu.daehakro.domain.entity.*;
import project.cnu.daehakro.domain.enums.EventType;
import project.cnu.daehakro.domain.enums.ResponseEnum;
import project.cnu.daehakro.domain.handler.CustomApiException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final static String DEFAULT_ROOM_TITLE = "좋은 사람, 좋은 시간, 좋은대화";
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UnivRepository univRepository;
    private final EventRepository eventRepository;
    private final TeamEventRepository teamEventRepository;


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
        EventMatcher<Member> memberEventMatcher = new EventMatcher<>(event.getMembersOfMan(), event.getMembersOfWomen());
        List<List<Member>> selectedCouples = memberEventMatcher.getSelectedCouples();

        for(List<Member> couple : selectedCouples) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .members(couple)
                    .title(DEFAULT_ROOM_TITLE)
                    .build();
            couple.stream()
                    .forEach(Member::match);
            chatRoomRepository.save(chatRoom);
        }

    }


    @Override
    public void randomTeamMatch(Long eventId) {
        TeamEvent teamEvent = teamEventRepository.findById(eventId).orElseThrow(
                () -> new CustomApiException(ResponseEnum.EVENT_NOT_EXIST)
        );
        if (!isEnd(teamEvent.getEndDate())) {
            throw new CustomApiException(ResponseEnum.EVENT_NOT_ENDED);
        }
        EventMatcher<Team> memberEventMatcher = new EventMatcher<>(teamEvent.getManTeams(), teamEvent.getWomenTeams());
        List<List<Team>> selectedCouples = memberEventMatcher.getSelectedCouples();

        for(List<Team> coupleTeam : selectedCouples) {
            List<Member> members = new ArrayList<>();
            coupleTeam.stream().map(
                    t -> members.addAll(t.getMembers())
            );
            ChatRoom chatRoom = ChatRoom.builder()
                    .members(members)
                    .title(DEFAULT_ROOM_TITLE)
                    .build();
            members.stream()
                    .forEach(Member::match);
            chatRoomRepository.save(chatRoom);
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
                .eventType(eventDto.getEventType())
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
                                .eventType(e.getEventType().name())
                                .build())
                .collect(Collectors.toList());
    }


}
