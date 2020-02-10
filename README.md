Arquillian deploying into managed wildfly + IninfinispanServerRule running with container mode

Reproducer for https://issues.redhat.com/browse/JDG-3506
##### How to run?

`mvn clean verify -Dserver.output.dir=/path/to/ispn-server -Dorg.infinispan.test.server.external=true`
