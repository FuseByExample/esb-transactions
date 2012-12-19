package org.fusesource.example.transactions.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.fusesource.example.transactions.database.Flight;

import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: gert
 * Date: 27/11/12
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */
public class TransactionalRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("amq://Input.Orders?username=admin&password=admin")
                .transacted()
                .to("log:Orders?showAll=true")
                .process(new ConvertToJpaBeanProcessor())
                .to("jpa://org.fusesource.example.transactions.database.Flight");
    }

    private class ConvertToJpaBeanProcessor implements Processor {

        public void process(Exchange exchange) throws Exception {
            exchange.getOut().copyFrom(exchange.getIn());

            String number = exchange.getIn().getBody(String.class);

            Flight flight = new Flight();
            flight.setNumber(number);
            flight.setDeparture("BRU");
            flight.setArrival("LON");
            flight.setDate(new Date());

            if (new Random().nextInt(3) == 2) {
                System.out.println("Uh oh, Murphy's Law in action - let's try to make this fail!");

                // setting the flight number to null - this will cause the INSERT to fail!
                flight.setNumber(null);
            }

            exchange.getOut().setBody(flight);
        }

    }
}
