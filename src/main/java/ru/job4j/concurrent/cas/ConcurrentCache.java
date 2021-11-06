package ru.job4j.concurrent.cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentCache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (k, v) -> {
            int cacheBaseVersion = v.getVersion();
            if (cacheBaseVersion != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base outputBase = new Base(v.getId(), cacheBaseVersion + 1);
            outputBase.setName(model.getName());
            return outputBase;
        }) == null;
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