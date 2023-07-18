package project.cnu.daehakro.domain.chat.dto;

import lombok.*;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.format.annotation.DateTimeFormat;
import project.cnu.daehakro.domain.entity.Event;
import project.cnu.daehakro.domain.enums.EventType;

import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
public class EventResDto {

    private final long eventId;

    private final String eventName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate endDate;

    private final Timestamp createAt;

    private final int manApply;

    private final int womanApply;

    private final int maxApply;

    private final boolean open;

    private final String eventType;

    @Builder
    public EventResDto(Long eventId, String eventName, LocalDate startDate, LocalDate endDate, Timestamp createAt, int manApply, int womanApply, int maxApply, boolean open, EventType eventType) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createAt = createAt;
        this.manApply = manApply;
        this.womanApply = womanApply;
        this.maxApply = maxApply;
        this.open = open;
        this.eventType = eventType.name();
    }

    // public EventResDto(Event event, int manApply, int womanApply) {
    //     this.eventId = event.getEventId();
    //     this.eventName = event.getEventName();
    //     this.startDate = event.getStartDate();
    //     this.endDate = event.getEndDate();
    //     this.createAt = event.getCreateAt();
    //     this.manApply = manApply;
    //     this.womanApply = womanApply;
    //     this.maxApply = event.getMaxApply();
    //     this.open = event.isOpen();
    // }
}
