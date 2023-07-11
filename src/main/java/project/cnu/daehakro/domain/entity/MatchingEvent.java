package project.cnu.daehakro.domain.entity;

import javax.persistence.*;

@Entity
public class MatchingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    private String eventName;



}
