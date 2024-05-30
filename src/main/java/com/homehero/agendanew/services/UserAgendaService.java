package com.homehero.agendanew.services;

import com.homehero.agendanew.DTOagenda.AvailabilityDTO;
import com.homehero.agendanew.DTOagenda.ClientAgendaDTO;
import com.homehero.agendanew.DTOagenda.HeroAgendaDTO;
import com.homehero.agendanew.DTOagenda.UserAgendaDTO;
import com.homehero.agendanew.models.HeroAgenda;
import com.homehero.agendanew.models.User_agenda;
import com.homehero.agendanew.repositories.UserAgendaRepo;
import com.homehero.agendanew.utils.AgendaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserAgendaService {

    @Autowired
    private UserAgendaRepo userAgendaRepo;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RestTemplate restTemplate;

    public void schedule_visit(UserAgendaDTO event) {
        User_agenda new_event = new User_agenda(event, true);
        userAgendaRepo.save(new_event);
    }

    public void schedule_quote(UserAgendaDTO event) {
        User_agenda new_event = new User_agenda(event, false);
        userAgendaRepo.save(new_event);
    }

    public List<AvailabilityDTO> getAvailableDays(int heroId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("FindAndInsertNumber");
        HeroAgenda agenda = getHeroAgenda(heroId);
        query.registerStoredProcedureParameter("p_max_iterations", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("dias_semana", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("horas_trabajo", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("heroe_id", Integer.class, ParameterMode.IN);

        query.setParameter("p_max_iterations", 30);
        query.setParameter("dias_semana", AgendaUtils.getDaysOfWork(agenda.getDaysOfWeek()));
        query.setParameter("horas_trabajo", AgendaUtils.getHoursOfWork(agenda.getInit_hour(), agenda.getEnd_hour()));
        query.setParameter("heroe_id", heroId);

        query.execute();

        return AgendaUtils.getScheduleFromQuery(query.getResultList());
    }

    public HeroAgenda getHeroAgenda(int hero_id) {
        String url = "https://msusuarios-zaewler4iq-uc.a.run.app/User/GetHeroAgenda?hero_id=" + hero_id;
        return restTemplate.getForObject(url, HeroAgenda.class);
    }

    public List<ClientAgendaDTO> getClientEvents(int clientId) {
        List<ClientAgendaDTO> events = new ArrayList<>();
        String q = String.format("select ua from User_agenda ua where client_id= %s and status=1", clientId);
        TypedQuery<User_agenda> query = entityManager.createQuery(q, User_agenda.class);
        for (User_agenda u : query.getResultList()) {
            ClientAgendaDTO newEvent = new ClientAgendaDTO();
            newEvent.setHour(u.getHour());
            String theDate = String.join("-", Integer.toString(u.getYear()), Integer.toString(u.getMonth()), Integer.toString(u.getDay()));
            newEvent.setEvent_date(theDate);
            newEvent.setHero_name(getHeroName(u.getHero_id()));
            newEvent.setHero_service_title(getHeroServiceTitle(u.getEvent_id(), u.is_visit()));
            events.add(newEvent);
        }
        return events;
    }

    public List<HeroAgendaDTO> getHeroEvents(int heroId) {
        List<HeroAgendaDTO> events = new ArrayList<>();
        String q = String.format("select ua from User_agenda ua where hero_id= %s and status=1", heroId);
        TypedQuery<User_agenda> query = entityManager.createQuery(q, User_agenda.class);
        for (User_agenda u : query.getResultList()) {
            HeroAgendaDTO newEvent = new HeroAgendaDTO();
            newEvent.setHour(u.getHour());
            String theDate = String.join("-", Integer.toString(u.getYear()), Integer.toString(u.getMonth()), Integer.toString(u.getDay()));
            newEvent.setEvent_date(theDate);
            newEvent.setClient_name(getClientName(u.getClient_id()));
            newEvent.setHero_service_title(getHeroServiceTitle(u.getEvent_id(), u.is_visit()));
            events.add(newEvent);
        }
        return events;
    }

    public String getHeroName(int hero_id) {
        String url = "https://msusuarios-zaewler4iq-uc.a.run.app/User/GetHeroName?hero_id=" + hero_id;
        return restTemplate.getForObject(url, String.class);
    }

    public String getClientName(int client_id) {
        String url = "https://msusuarios-zaewler4iq-uc.a.run.app/User/GetClientName?client_id=" + client_id;
        return restTemplate.getForObject(url, String.class);
    }

    public String getHeroServiceTitle(int event_id, boolean is_visit) {
        String url = "";
        if(is_visit) {
            url = "https://mssolicitud-zaewler4iq-ue.a.run.app/Solicitudes/GetVisitTitle?event_id=" + event_id;
        }else {
            url = "https://mssolicitud-zaewler4iq-ue.a.run.app/Solicitudes/GetQuoteTitle?event_id=" + event_id;
        }
        return restTemplate.getForObject(url, String.class);
    }
}
