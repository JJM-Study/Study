package com.jm.p_ai.domain;

import jakarta.persistence.*;

@Entity
public class AI_User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String username;

        public Long getId() {

            return id;
        }

        public void setId(Long id) {

            this.id = id;
        }

        public String getUsername() {

            return username;
        }

        public void setUsername(String username) {

            this.username = username;
        }

}
