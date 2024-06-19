package com.jm.p_ai.domain;

import jakarta.persistence.*;

public class AI_Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;

    @ManyToOne
    private Question question;

}
