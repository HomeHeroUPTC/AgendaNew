package com.homehero.agendanew.models;

import com.homehero.agendanew.DTOagenda.UserAgendaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User_agenda {

    public User_agenda(UserAgendaDTO userAgendaDTO, boolean is_visit) {
        String[] date_split = userAgendaDTO.getEvent_date().split("-");

        this.year = Integer.parseInt(date_split[0]);
        this.month = Integer.parseInt(date_split[2]);
        this.day = Integer.parseInt(date_split[1]);
        this.hour = userAgendaDTO.getHour();
        this.is_visit = is_visit;
        this.hero_id = userAgendaDTO.getHero_id();
        this.client_id = userAgendaDTO.getClient_id();
        this.event_id = userAgendaDTO.getEvent_id();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int hero_id;

    @Column(nullable = false)
    private int client_id;

    @Column(nullable = false)
    private int event_id;

    @Column(nullable = false)
    private boolean is_visit;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int day;

    @Column(nullable = false)
    private int hour;

}
