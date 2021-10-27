package ru.job4j.concurrent.jcip.users;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        Integer id = user.getId();
        return storage.putIfAbsent(id, user) == null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized boolean update(User user) {
        Integer id = user.getId();
        return storage.replace(id, storage.get(id), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User inUser = storage.get(fromId);
        User toUser = storage.get(toId);
        if (inUser != null && toUser != null) {
            int inAmount = inUser.getAmount();
            int toAmount = toUser.getAmount();
            if (inAmount >= amount) {
                inUser.setAmount(inAmount - amount);
                toUser.setAmount(toAmount + amount);
                return true;
            }
            return false;
        }
        return false;
    }
}