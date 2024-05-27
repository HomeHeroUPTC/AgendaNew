package com.homehero.agendanew.utils;

import com.homehero.agendanew.DTOagenda.AvailabilityDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AgendaUtils {
    public static int[] convertStringToIntArray(String numbersString) {
        return Arrays.stream(numbersString.split(",\\s*"))
                .filter(s -> !s.isEmpty()) // Filtrar partes vacías
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static String getDaysOfWork(String days) {
        return Arrays.stream(days.split(","))
                .map(AgendaUtils::getNumberDayOfWeek)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static String getHoursOfWork(int begin, int end) {
        return IntStream.rangeClosed(begin, end)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(","));
    }

    public static int getNumberDayOfWeek(String letra) {
        switch (letra.toUpperCase()) {
            case "D":
                return 1; // Domingo
            case "L":
                return 2; // Lunes
            case "M":
                return 3; // Martes
            case "X":
                return 4; // Miércoles
            case "J":
                return 5; // Jueves
            case "V":
                return 6; // Viernes
            case "S":
                return 7; // Sábado
            default:
                throw new IllegalArgumentException("Letra de día no válida: " + letra);
        }
    }

    public static List<AvailabilityDTO> getScheduleFromQuery(List<Object[]> result) {
        List<AvailabilityDTO> schedule = new ArrayList<>();
        for(Object[] row : result) {
            //day, month, year, hours
            AvailabilityDTO a = new AvailabilityDTO();
            String day = String.format("%s-%s-%s", row[2].toString(), row[1].toString(), row[0].toString());
            a.setDay(day);
            a.setHours(AgendaUtils.convertStringToIntArray(row[3].toString()));
            schedule.add(a);
        }
        return schedule;
    }
}
