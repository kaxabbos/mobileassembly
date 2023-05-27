package com.mobileassembly.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Serves {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "serves", fetch = FetchType.LAZY)
    private List<Devices> devices;
    @OneToOne(fetch = FetchType.LAZY)
    private Users user;
    public Serves(Users user) {
        devices = new ArrayList<>();
        this.user = user;
    }
}
