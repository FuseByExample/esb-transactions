package org.fusesource.example.transactions.database;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for a flight
 */
@Entity
@Table(name = "FLIGHTS")
public class Flight {

    private Long id;
    private String number;
    private String from;
    private String to;
    private Date date;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
