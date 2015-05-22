# Datasource bundle
This bundle defines the JDBC `DataSource`s that will be used for accessing the 
relational database.


## Blueprint
We are using Blueprint to define the data source bean and publish it into the OSGi Service Registry.  Have a look at
`src/main/resources/OSGI-INF/blueprint/datasource.xml` for more details about how this done.


## Checking the OSGi Service Registry
After the bundle is started, we can use the Fuse ESB Enterprise console to look at the registered objects in the registry.

First, find the bundle id for the bundle called "Fuse By Example :: Transactions :: Datasource" by using the `osgi:list` command.
You can use `grep` to filter the list of bundles and quickly find the right one.

    JBossFuse:admin@root> osgi:list | grep Transactions
    [ 298] [Active     ] [Created     ] [       ] [   80] JBoss Fuse :: Examples :: Transactions :: Datasource (6.2.0)
    [ 299] [Active     ] [            ] [       ] [   80] JBoss Fuse :: Examples :: Transactions :: Database (6.2.0)
    [ 300] [Active     ] [Created     ] [       ] [   80] JBoss Fuse :: Examples :: Transactions :: Routing (6.2.0)


In this example, the bundle id is 298.  Using the `osgi:ls` command, we can see that this bundle is publishing 3 services:

* first, there's the `javax.sql.XADataSource` that we created in our Blueprint XML file
* secondly, Aries JTA has added a corresponding `javax.sql.DataSource` and added the `aries.xa.aware = true` property to it to indicate an XA-aware data source
* finally, the Blueprint extender mechanism also published the Blueprint container (containg our data source bean definitions) it created

This is what the `osgi:ls` output looks like

    JBossFuse:admin@root> osgi:ls 298
    
    JBoss Fuse :: Examples :: Transactions :: Datasource (298) provides:
    --------------------------------------------------------------------
    Bundle-SymbolicName = datasource
    Bundle-Version = 6.2.0
    objectClass = [org.osgi.service.cm.ManagedService]
    service.id = 629
    service.pid = org.jboss.fuse.examples.persistence2
    ----
    aries.xa.exceptionSorter = known
    aries.xa.name = transactionsdbxa
    aries.xa.password = fuse
    aries.xa.pooling = true
    aries.xa.poolMaxSize = 10
    aries.xa.poolMinSize = 0
    aries.xa.transaction = xa
    aries.xa.username = fuse
    objectClass = [javax.sql.XADataSource]
    osgi.jndi.service.name = jdbc/transactionsdbxa
    osgi.service.blueprint.compname = xaDataSource
    service.id = 630
    ----
    aries.managed = true
    aries.xa.aware = true
    aries.xa.exceptionSorter = known
    aries.xa.name = transactionsdbxa
    aries.xa.password = fuse
    aries.xa.pooling = true
    aries.xa.poolMaxSize = 10
    aries.xa.poolMinSize = 0
    aries.xa.transaction = xa
    aries.xa.username = fuse
    objectClass = [javax.sql.DataSource]
    osgi.jndi.service.name = jdbc/transactionsdbxa
    osgi.service.blueprint.compname = xaDataSource
    service.id = 631
    service.ranking = 1000
    ----
    aries.xa.exceptionSorter = known
    aries.xa.name = transactionsdb
    aries.xa.password = fuse
    aries.xa.pooling = true
    aries.xa.poolMaxSize = 10
    aries.xa.poolMinSize = 0
    aries.xa.transaction = local
    aries.xa.username = fuse
    objectClass = [javax.sql.DataSource]
    osgi.jndi.service.name = jdbc/transactionsdb
    osgi.service.blueprint.compname = dataSource
    service.id = 632
    ----
    aries.managed = true
    aries.xa.exceptionSorter = known
    aries.xa.name = transactionsdb
    aries.xa.password = fuse
    aries.xa.pooling = true
    aries.xa.poolMaxSize = 10
    aries.xa.poolMinSize = 0
    aries.xa.transaction = local
    aries.xa.username = fuse
    objectClass = [javax.sql.DataSource]
    osgi.jndi.service.name = jdbc/transactionsdb
    osgi.service.blueprint.compname = dataSource
    service.id = 633
    service.ranking = 1000
    ----
    objectClass = [org.osgi.service.blueprint.container.BlueprintContainer]
    osgi.blueprint.container.symbolicname = datasource
    osgi.blueprint.container.version = 6.2.0
    service.id = 634


