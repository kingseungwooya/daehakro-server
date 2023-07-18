package project.cnu.daehakro.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "univ_info")
public class UnivInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "univ_id")
    private Long univId;

    private String domain;

    private String name;

    private String place;
    @Builder
    public UnivInfo(String domain, String name, String place) {
        this.domain = domain;
        this.name = name;
        this.place = place;
    }
}
