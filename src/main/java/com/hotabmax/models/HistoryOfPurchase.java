package com.hotabmax.models;

import javax.persistence.*;
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
    private String date;
    @Column
    private String time;
    @Column
    private String logistname;

    public HistoryOfPurchase(){

    }

    public HistoryOfPurchase(String name, int amount, String logistname){
        this.name = name;
        this.amount = amount;
        this.date = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        this.time = new SimpleDateFormat("HH:mm:ss").format(new Date());
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

    public void setLogistName(String logistname){
        this.logistname = logistname;
    }

    public String getLogistName(){
        return this.logistname;
    }

}
