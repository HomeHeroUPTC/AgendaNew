package com.homehero.agendanew.DTOagenda;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AvailabilityDTO {

    private String day;
    private int[] hours;

    public AvailabilityDTO(String day, int[] hours) {
        this.day = day;
        this.hours = hours;
    }
}