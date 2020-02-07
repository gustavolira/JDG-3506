Arquillian deploying into managed wildfly + IninfinispanServerRule running with container mode

Reproducer for https://issues.redhat.com/browse/JDG-3506
##### How to run?

`mvn clean verify -Dserver.output.dir=/home/gustavo/git/infinispan/server/runtime/target/infinispan-server-10.1.3-SNAPSHOT -Dorg.infinispan.test.server.external=true`