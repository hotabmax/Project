package com.hotabmax.models;

import javax.persistence.*;

@Entity
@Table(name = "userdata")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private Integer roleid;

    public User() {

    }

    public User(String name, String password, int roleid) {
        this.name = name;
        this.password = password;
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() { return roleid; }

    void setRoleId(int roleid) { this.roleid = roleid; }
}
