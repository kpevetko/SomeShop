package com.example.popra.model;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer idUser;
    private Integer idProduct;
    private Date date;
    private Integer costAll;
    private Integer numberAll;

    public Purchase() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCostAll() {
        return costAll;
    }

    public void setCostAll(int costAll) {
        this.costAll = costAll;
    }

    public int getNumberAll() {
        return numberAll;
    }

    public void setNumberAll(int numberAll) {
        this.numberAll = numberAll;
    }
}
