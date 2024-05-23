package com.homehero.agendanew.models;

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
public class Visit_agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private int hero_id;

    @Column(nullable = false)
    private int client_id;

    @Column(nullable = false)
    private int visit_id;

    @Column(nullable = false, length = 11)
    private String visit_date;

    @Column(nullable = false)
    private int init_time;

    @Column(nullable = false, length = 50)
    private String address;
}
