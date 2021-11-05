package ru.job4j.concurrent.cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class ConcurrentCache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        BiFunction<Integer, Base, Base> biFunction = new BiFunction<Integer, Base, Base>() {
            @Override
            public Base apply(Integer integer, Base cacheBase) {
                int cacheBaseVersion = cacheBase.getVersion();
                if (cacheBaseVersion != model.getVersion()) {
                    throw new OptimisticException("Versions are not equal");
                }
                Base outputBase = new Base(cacheBase.getId(), cacheBaseVersion + 1);
                outputBase.setName(model.getName());
                return outputBase;
            }
        };
        memory.computeIfPresent(model.getId(), biFunction);
        return true;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public String getName(Integer key) {
        return memory.get(key).getName();
    }
}

class Base {
    private final int id;
    private final int version;
    private String name;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class OptimisticException extends RuntimeException {

    public OptimisticException(String message) {
        super(message);
    }
}