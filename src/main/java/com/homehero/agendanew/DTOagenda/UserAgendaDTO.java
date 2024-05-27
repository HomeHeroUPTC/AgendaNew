package com.homehero.agendanew.DTOagenda;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAgendaDTO {

    private int id;

    private int hero_id;

    private int client_id;

    private int event_id;

    private String event_date;

    private int hour;

}
