package com.homehero.agendanew.controller;

import com.homehero.agendanew.DTOagenda.AvailabilityDTO;
import com.homehero.agendanew.DTOagenda.UserAgendaDTO;
import com.homehero.agendanew.models.ErrorResponse;
import com.homehero.agendanew.services.UserAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/Agenda")
public class Controller {
    @Autowired
    private UserAgendaService userAgendaService;

    @PostMapping(value = "/Schedule_visit")
    public ResponseEntity<?> schedule_visit(@RequestBody UserAgendaDTO event){
        try {
            userAgendaService.schedule_visit(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("An error occurred while fetching services: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/Schedule_quote")
    public ResponseEntity<?> schedule_quote(@RequestBody UserAgendaDTO event){
        try {
            userAgendaService.schedule_quote(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("An error occurred while fetching services: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Get Next 30 Days with free hours for visit and quotes
    @GetMapping(value = "/GetAvailableHeroDays")
    public ResponseEntity<?> GetAvailableHeroDays(@RequestParam  int hero_id) {
        try {
            List<AvailabilityDTO> services = userAgendaService.getAvailableDays(hero_id);
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("An error occurred while fetching services: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
