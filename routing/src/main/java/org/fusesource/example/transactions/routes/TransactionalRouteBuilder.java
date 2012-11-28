package org.fusesource.example.transactions.routes;

import org.apache.camel.builder.RouteBuilder;

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
        from("amq://Input.Orders?username=admin&password=admin").transacted().to("log:Orders?showAll=true");
    }

}
