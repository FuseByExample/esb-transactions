package org.fusesource.example.transactions.database;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for a flight
 */
@Entity
@Table(name = "FLIGHTS")
public class Flight {

    private String number;
    private String departure;
    private String arrival;
    private Date date;

    @Id
    @Column(length = 12)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(length = 3)
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @Column(length = 3)
    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
