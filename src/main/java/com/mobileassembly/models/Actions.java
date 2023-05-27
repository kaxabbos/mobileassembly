package com.mobileassembly.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Actions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String action;
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users user;

    public Actions(String action, String date, Users user) {
        this.action = action;
        this.date = date;
        this.user = user;
    }
}
