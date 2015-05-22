# Routing bundle
In this bundle, we will bring together the services we set up in all our other bundles inside one Camel route.  We will
receive JMS messages, transform them into a `Flight` POJO and then persist those into the database using JPA.

For coördinating the transactions with the JMS broker and the RDBMS, we will use Fuse ESB Enterprise's built-in JTA support
(based Aries JTA).

## Blueprint XML
The Blueprint XML file at `src/main/java/resources/OSGI-INF/blueprint/routing.xml' is where we glue everything together.

First, we retrieve some of the services available in the OSGi Service Registry:

* the global transaction manager provided by Fuse ESB Enterprise
* the JPA EntityManagerFactory that we set up ourselves in the `database` module

Afterwards, we define our two Camel components and link them up with the correct shared services:

* the JMS component is defined with an XA-aware connection factory and a reference to the global transaction manager
* the JPA component is defined with a reference to the JPA EntityManagerFactory

The Blueprint XML file will also define the CamelContext itself, the Camel routes themselves are defined in a Java class.

## TransactionalRouteBuilder
The `src/main/java/org/jboss/fuse/examples/transactions/routes/TransactionalRouteBuilder.java` class defines the actual
Camel routes.  The code looks like a plain route from a JMS endpoint to a JPA endpoint, but there 2 little things to notice here:

* the class extends SpringRouteBuilder, to allow for seamless integration between the route and Spring's support for transactions
* because the route starts with a transaction-enabled endpoint amq://Input.Flights, a new transaction will _automatically_ be started whenever a message gets pulled from the queue. There is no need to call the `transacted()` DSL method.
