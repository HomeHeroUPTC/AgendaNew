package com.homehero.agendanew.repositories;

import com.homehero.agendanew.models.User_agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAgendaRepo extends JpaRepository<User_agenda, Integer> {

}
