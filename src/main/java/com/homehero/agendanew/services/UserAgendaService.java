package com.homehero.agendanew.services;

import com.homehero.agendanew.DTOagenda.AvailabilityDTO;
import com.homehero.agendanew.DTOagenda.UserAgendaDTO;
import com.homehero.agendanew.models.HeroAgenda;
import com.homehero.agendanew.models.User_agenda;
import com.homehero.agendanew.repositories.UserAgendaRepo;
import com.homehero.agendanew.utils.AgendaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAgendaService {

    @Autowired
    private UserAgendaRepo userAgendaRepo;

    @Autowired
    private EntityManager entityManager;

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
        //get from profile ms
        return new HeroAgenda("L,M,X,J,V", 8, 17);
    }




}
