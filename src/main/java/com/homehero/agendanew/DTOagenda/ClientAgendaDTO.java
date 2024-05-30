package com.homehero.agendanew.DTOagenda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClientAgendaDTO {

    private String hero_name;

    private String hero_service_title;

    private String event_date;

    private int hour;

}
