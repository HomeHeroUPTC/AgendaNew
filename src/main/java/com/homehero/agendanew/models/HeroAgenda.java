package com.homehero.agendanew.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HeroAgenda {
    private String daysOfWeek;
    private int init_hour;
    private int end_hour;
}
