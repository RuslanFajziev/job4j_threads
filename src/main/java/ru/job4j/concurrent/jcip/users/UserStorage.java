package ru.job4j.concurrent.jcip.users;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    private synchronized boolean find(Integer key) {
        return storage.containsKey(key);
    }

    public synchronized boolean add(User user) {
        Integer id = user.getId();
        return storage.putIfAbsent(id, user) == null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized boolean update(User user) {
        Integer id = user.getId();
        if (storage.containsKey(id)) {
            storage.put(id, user);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User inUser = storage.get(fromId);
        User toUser = storage.get(toId);
        if (inUser != null && toUser != null) {
            int inAmount = inUser.getAmount();
            int toAmount = toUser.getAmount();
            if (inAmount >= amount) {
                inUser = new User(fromId, inAmount - amount);
                toUser = new User(toId, toAmount + amount);
                update(inUser);
                update(toUser);
                return true;
            }
            return false;
        }
        return false;
    }
}