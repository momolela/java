package com.momolela.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) {
        CacheManager cacheManager = CacheManager.create();
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("10s");
        cacheConfiguration.setMaxElementsInMemory(100);
        cacheConfiguration.setOverflowToDisk(false);
        cacheConfiguration.setEternal(false);
        cacheConfiguration.setTimeToLiveSeconds(10);
        cacheConfiguration.setTimeToIdleSeconds(10);
        CacheConfiguration.CacheEventListenerFactoryConfiguration cacheEventListenerFactoryConfiguration = new CacheConfiguration.CacheEventListenerFactoryConfiguration();
        cacheEventListenerFactoryConfiguration.className(MyCacheEventListenerFactory.class.getName());
        cacheConfiguration.cacheEventListenerFactory(cacheEventListenerFactoryConfiguration);
        Cache cacheStore = new Cache(cacheConfiguration);
        cacheManager.addCache(cacheStore);


        // Cache cacheStore = new Cache("10s", 100, false, false, 10L, 10L);
        // cacheManager.addCache(cacheStore);

        Element sunzj = new Element("sunzj", "27");
        Element hufy = new Element("hufy", "26");
        cacheStore.put(sunzj);
        cacheStore.put(hufy);


        try {
            System.out.println("cache set ok");
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
