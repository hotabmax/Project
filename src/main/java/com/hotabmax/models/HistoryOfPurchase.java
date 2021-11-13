package com.hotabmax.models;

import javax.persistence.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "historyofpurchase")
public class HistoryOfPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer amount;
    @Column
    private Date date;
    @Column
    private Time time;
    @Column
    private String logistname;

    public HistoryOfPurchase(){

    }

    public HistoryOfPurchase(String name, int amount, Date date, Time time, String logistname){
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.time = time;
        this.logistname = logistname;
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

    public void setDate(Date date){
        this.date = date;
    }

    public String getDate(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.date);
    }

    public void setTime(Time time){
        this.time = time;
    }

    public String getTime(){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(this.time);
    }

    public void setLogistName(String logistname){
        this.logistname = logistname;
    }

    public String getLogistName(){
        return this.logistname;
    }
}
