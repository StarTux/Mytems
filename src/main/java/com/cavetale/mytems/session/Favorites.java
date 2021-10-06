package com.cavetale.mytems.session;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class Favorites {
    protected Map<Class<?>, Object> map = new HashMap<>();

    public <T> T getOrSet(Class<T> clz, Supplier<T> dfl) {
        Object o = map.get(clz);
        if (o != null && clz.isInstance(o)) return clz.cast(o);
        T result = dfl.get();
        map.put(clz, result);
        return result;
    }

    public void set(Object value) {
        map.put(value.getClass(), value);
    }

    public <T> T get(Class<T> clz) {
        Object result = map.get(clz);
        return clz.isInstance(result)
            ? clz.cast(result)
            : null;
    }

    public void clear(Class<?> clz) {
        map.remove(clz);
    }
}
