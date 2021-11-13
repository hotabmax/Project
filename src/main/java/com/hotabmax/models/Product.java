package com.hotabmax.models;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private Integer amount;
    @Column
    private Integer purchaseprice;
    @Column
    private Integer sellingprice;
    @Column
    private String description;
    @Column
    private Integer sortid;

    public Product() {

    }

    public Product(String name, int amount,int purchaseprice, int sellingprice, String description, int sortid) {
        this.name = name;
        this.amount = amount;
        this.purchaseprice = purchaseprice;
        this.sellingprice = sellingprice;
        this.description = description;
        this.sortid = sortid;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPurchasePrice(){
        return this.purchaseprice;
    }

    public void setPurchasePrice(int purchaseprice){
        this.purchaseprice = purchaseprice;
    }

    public int getSellingPrice(){
        return this.sellingprice;
    }

    public void setSellingPrice(int sellingprice){
        this.sellingprice = sellingprice;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(Integer sortid) {
        this.sortid = sortid;
    }
}
