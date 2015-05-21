/**
 *  Copyright 2005-2015 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package org.jboss.fuse.examples.transactions.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A very simple entity class for a flight - every flight has:
 * - a flight number
 * - a departure airport
 * - an arrival airport
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "FLIGHTS")
public class Flight implements Serializable {

    @Id
    @Column(length = 12)
    private String number;
    @Column(length = 3)
    private String departure;
    @Column(length = 3)
    private String arrival;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return String.format("[flight %s from %s to %s]", number, departure, arrival);
    }

}
