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
package org.jboss.fuse.examples.transactions.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.jboss.fuse.examples.transactions.model.Flight;

import static org.jboss.fuse.examples.transactions.routes.Airports.randomAirport;

/**
 * Camel route builder defining our transactional route.  Because we want to maximize the level of support Spring offers for transactions,
 * we are extending SpringRouteBuilder instead of a plain RouteBuilder.
 *
 * Transaction demarcation (begin() and commit()) is performed by the transaction-aware 'amq' endpoint. Hence, there is no need to add the transacted() DSL keyword to the route.
 */
public class TransactionalRouteBuilder extends SpringRouteBuilder {

    @Override
    public void configure() throws Exception {
        from("amq://Input.Flights?username=admin&password=admin")
                .log("Received JMS message ${body}")
                .process(new ConvertToJpaBeanProcessor())
                .log("Storing ${body} in the database")
                .to("jpa://org.jboss.fuse.examples.transactions.model.Flight");
    }

    /*
     * Just a simple Camel processor to transform a plain text message into a Flight object.
     */
    private class ConvertToJpaBeanProcessor implements Processor {

        public void process(Exchange exchange) throws Exception {
            exchange.getOut().copyFrom(exchange.getIn());

            String number = exchange.getIn().getBody(String.class);

            Flight flight = new Flight();
            flight.setNumber(number);
            flight.setDeparture(randomAirport());
            flight.setArrival(randomAirport());

            exchange.getOut().setBody(flight);
        }
    }
}
