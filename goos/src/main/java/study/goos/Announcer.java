package study.goos;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Announcer<T extends EventListener> {

    private final T proxy;
    private final List<T> listeners = new ArrayList<>();

    public Announcer(Class<? extends T> listenerType) {
        this.proxy = listenerType.cast(Proxy.newProxyInstance(
                listenerType.getClassLoader(),
                new Class[]{listenerType},
                (proxy, method, args) -> {
                    Announcer.this.announce(method, args);
                    return null;
                }
        ));
    }

    public static <T extends EventListener> Announcer<T> to(Class<? extends T> listenerType) {
        return new Announcer<>(listenerType);
    }

    public void addListener(T listener) {
        this.listeners.add(listener);
    }

    public void removeListener(T listener) {
        this.listeners.remove(listener);
    }

    public T announce() {
        return this.proxy;
    }

    private void announce(Method method, Object[] args) {
        try {
            for (T listener : this.listeners) {
                method.invoke(listener, args);
            }

        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("could not invoke listener", e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            } else if (cause instanceof Error) {
                throw (Error) cause;
            } else {
                throw new UnsupportedOperationException("listener threw exception", cause);
            }
        }
    }
}
