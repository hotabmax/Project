package com.hotabmax.models;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "historyofselling")
public class HistoryOfSelling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer amount;
    @Column
    private String date;
    @Column
    private String time;
    @Column
    private String sellername;

    public HistoryOfSelling(){

    }

    public HistoryOfSelling(String name, int amount, String sellername){
        this.name = name;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        this.time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        this.sellername = sellername;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public int getAmount(){
        return this.amount;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return this.date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime(){
        return this.time;
    }

    public void setSellerName(String sellername){
        this.sellername = sellername;
    }

    public String getSellerName(){
        return this.sellername;
    }

}
