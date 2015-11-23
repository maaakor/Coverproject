package com.springapp.test.client;

/**
 * Created by kay on 2015/11/19.
 */

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;

public class ThriftHttpProxyFactoryBean extends ThriftClientInterceptor implements FactoryBean<Object> {

    private Object serviceProxy;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = new ProxyFactory(getServiceInterface(), this).getProxy(getBeanClassLoader());
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }
}