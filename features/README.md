# Features
This project creates a features definition that can be used to install the example.

## How it works?
Using Maven's resource filtering mechanism, we replace the `${xyz.version}` tokens in the `src/main/resources/features.xml` file.
Afterwards, the resulting file is published as a Maven artifact itself so you can use it from within the Fuse ESB console with

    FuseESB:karaf@root> features:addurl mvn:org.fusesource.example.transactions/features/1.0-SNAPSHOT/xml/features

Inside the features file, you'll find a definition for feature called `transactions-openjpa-demo`.