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

    FuseESB:karaf@root> osgi:list | grep Transactions
    [ 268] [Active     ] [Created     ] [       ] [   60] Fuse By Example :: Transactions :: Datasource (1.0.0.SNAPSHOT)
    [ 269] [Active     ] [            ] [       ] [   60] Fuse By Example :: Transactions :: Database (1.0.0.SNAPSHOT)
    [ 270] [Active     ] [            ] [Started] [   60] Fuse By Example :: Transactions :: Routing (1.0.0.SNAPSHOT)

In this example, the bundle id is 269.  Using the `osgi:ls` command, you can see the `javax.persistence.EntityManagerFactory` listed as
a service.

    FuseESB:karaf@root> osgi:ls 269

    Fuse By Example :: Transactions :: Database (269) provides:
    -----------------------------------------------------------
    objectClass = javax.persistence.EntityManagerFactory
    org.apache.aries.jpa.container.managed = true
    org.apache.aries.jpa.default.unit.name = false
    osgi.unit.name = datasourceExample
    osgi.unit.provider = org.apache.openjpa.persistence.PersistenceProviderImpl
    osgi.unit.version = 1.0.0.SNAPSHOT
    service.id = 418