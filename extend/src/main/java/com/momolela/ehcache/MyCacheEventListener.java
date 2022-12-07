package com.momolela.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class MyCacheEventListener implements CacheEventListener {
    @Override
    public void notifyElementRemoved(Ehcache ehcache, Element element) throws CacheException {

    }

    @Override
    public void notifyElementPut(Ehcache ehcache, Element element) throws CacheException {
        System.out.println("name ==========" + ehcache.getName());
        System.out.println("key ==========" + element.getObjectKey());
        System.out.println("value ==========" + element.getObjectValue());
        System.out.println("timeToIdle ==========" + element.getTimeToIdle());
        System.out.println("timeToLive ==========" + element.getTimeToLive());
    }

    @Override
    public void notifyElementUpdated(Ehcache ehcache, Element element) throws CacheException {

    }

    /**
     * 到过期不会准时触发，需要手动调用 cache.get() 之后才会触发
     * @param ehcache
     * @param element
     */
    @Override
    public void notifyElementExpired(Ehcache ehcache, Element element) {

        System.out.println("name ==========" + ehcache.getName());
        System.out.println("timeToIdle ==========" + element.getTimeToIdle());
        System.out.println("timeToLive ==========" + element.getTimeToLive());
    }

    @Override
    public void notifyElementEvicted(Ehcache ehcache, Element element) {

    }

    @Override
    public void notifyRemoveAll(Ehcache ehcache) {

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public void dispose() {

    }
}
