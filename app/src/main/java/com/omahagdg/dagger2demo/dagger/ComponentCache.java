package com.omahagdg.dagger2demo.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.String.format;

public class ComponentCache {

    /**
     * This class holds a map of cached components so they can be reused for injecting classes
     * in the same scope (list / forecast / etc.)
     *
     * Destroying a component will force a new one to be recreated the next time it's needed which
     * allows you to "forget" instances of the same scope
     */
    public static final String SERVICE_NAME = ComponentCache.class.getName();

    private Map<String, Object> componentMap;

    public ComponentCache() {
        componentMap = new LinkedHashMap<>();
    }

    public boolean hasComponentForTag(String tag) {
        for (String key : componentMap.keySet()) {
            if (key.equals(tag)) {
                return true;
            }
        }
        return false;
    }

    @NonNull
    public Object getComponentForTag(String tag) {
        Object component = componentMap.get(tag);
        if (component == null) {
            throw new NullPointerException(format("No component for tag: '%s' in cache", tag));
        }
        return component;
    }

    public void put(Scoped scoped, Object component) {
        componentMap.put(scoped.getScopeTag(), component);
    }

    public void destroyComponentForTag(String tag) {
        componentMap.remove(tag);
    }

    @SuppressWarnings("WrongConstant")
    public static ComponentCache get(Context context) {
        return (ComponentCache) context.getApplicationContext().getSystemService(SERVICE_NAME);
    }
}
