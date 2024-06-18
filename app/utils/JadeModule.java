package utils;

import com.google.inject.AbstractModule;

public class JadeModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(JadeUtil.class).asEagerSingleton();
    }
}
