package com.mobileassembly.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String file;
    private int count;
    private int price;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Incomes income;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Carts> carts;

    public Products(String name, int count, int price, String file) {
        this.name = name;
        this.count = count;
        this.price = price;
        this.file = file;
        this.income = new Incomes(this);
    }
}
