# Database bundle
This bundle defines the JPA entity classes as well as the JPA persistence unit.

## Persistence unit
The persistence unit is defined in the `src/main/resources/META-INF/persistence.xml` file.  Inside Fuse ESB Enterprise,
Aries JPA is responsible for configuring and activating this persistence unit.  In order for it to find out about the
persistence unit, an extra MANIFEST.MF header called `Meta-Persistence` has to be added.

## Checking the OSGi Service Registry
Once the persistence unit has been created, Aries JPA will also ensure that the corresponding `javax.persistence.EntityManagerFactory`
is exported into the OSGi Service Registry.  We can use the Fuse ESB Enterprise console to verify that this is actually the case.

First, find the bundle id for the bundle called "Fuse By Example :: Transactions :: Database" by using the `osgi:list` command.
You can use `grep` to filter the list of bundles and quickly find the right one.

   JBossFuse:karaf@root> osgi:list | grep -i transactions
   [ 298] [Active     ] [Created     ] [       ] [   80] JBoss Fuse :: Examples :: Transactions :: Datasource (6.2.0)
   [ 299] [Active     ] [            ] [       ] [   80] JBoss Fuse :: Examples :: Transactions :: Database (6.2.0)
   [ 300] [Active     ] [Created     ] [       ] [   80] JBoss Fuse :: Examples :: Transactions :: Routing (6.2.0)
   
In this example, the bundle id is 299.  Using the `osgi:ls` command, you can see the `javax.persistence.EntityManagerFactory` listed as
a service.

    JBossFuse:karaf@root> osgi:ls 299
    
    JBoss Fuse :: Examples :: Transactions :: Database (299) provides:
    ------------------------------------------------------------------
    objectClass = [javax.persistence.EntityManagerFactory]
    org.apache.aries.jpa.container.managed = true
    org.apache.aries.jpa.default.unit.name = false
    osgi.unit.name = transactionsExample
    osgi.unit.provider = org.hibernate.jpa.HibernatePersistenceProvider
    osgi.unit.version = 6.2.0
    service.id = 635

    
    