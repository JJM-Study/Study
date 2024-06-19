package com.jm.p_ai.domain;

import jakarta.persistence.*;

@Entity
public class AI_Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

}
