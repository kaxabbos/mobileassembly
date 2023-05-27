package com.mobileassembly.models;

import com.mobileassembly.models.enums.DeviceType;
import com.mobileassembly.models.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Devices {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private int cost;
    private int price;
    @Column(length = 5000)
    private String description;
    private String file;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users owner;
    @ManyToOne(fetch = FetchType.LAZY)
    private Serves serves;

    public Devices(String name, DeviceType deviceType, Status status, Users owner) {
        this.name = name;
        this.deviceType = deviceType;
        this.status = status;
        this.owner = owner;
        cost = 0;
        price = 0;
    }

    public int prof() {
        return (int) (((double) cost / price) * 100);
    }
}
