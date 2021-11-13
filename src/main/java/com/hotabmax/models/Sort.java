package com.hotabmax.models;

import javax.persistence.*;

@Entity
@Table(name = "sort")
public class Sort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    public Sort() {

    }

    public Sort(int id, String name) {
        this.id =(long) id;
        this.name = name;
    }

    public long getId() { return id; }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

}
