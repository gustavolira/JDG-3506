package com.example;

import static org.junit.Assert.assertEquals;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.server.test.*;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.nio.file.Paths;


@RunWith(Arquillian.class)
public class ServerTest {

   @ClassRule
   public static final InfinispanServerRule SERVERS =
           InfinispanServerRuleBuilder.config("infinispan-cluster.xml")
                   .numServers(1)
                   .runMode(ServerRunMode.CONTAINER)
                   .property(TestSystemPropertyNames.INFINISPAN_TEST_SERVER_BASE_IMAGE_NAME, "infinispan/server:11.0.0.Alpha1-3")
                   .build();

   @Rule
   public InfinispanServerTestMethodRule SERVER_TEST = new InfinispanServerTestMethodRule(SERVERS);

   @Deployment
   public static Archive<?> deployment() {
      return ShrinkWrap.create(WebArchive.class, "test.war")
              .addAsLibraries(
                      Maven.resolver().loadPomFromFile("pom.xml")
                              .importTestDependencies()
                              .resolve()
                              .withTransitivity()
                              .asFile());
   }



   @Test
   public void simpleTest() {
      RemoteCache<String, String> cache = SERVER_TEST.hotrod().withCacheMode(CacheMode.DIST_SYNC).create();
      cache.put("k1", "v1");
      assertEquals(1, cache.size());
      assertEquals("v1", cache.get("k1"));
   }

}
