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
        if (find(id)) {
            return false;
        } else {
            storage.put(id, user);
            return true;
        }
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized boolean update(User user) {
        Integer id = user.getId();
        if (find(id)) {
            storage.put(id, user);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Boolean input = find(fromId);
        Boolean output = find(toId);
        if (input && output) {
            int inputUserAmount = storage.get(fromId).getAmount();
            if (inputUserAmount >= amount) {
                int outputUserAmount = storage.get(toId).getAmount();
                User newUserInput = new User(fromId, inputUserAmount - amount);
                User newUserOutput = new User(toId, outputUserAmount + amount);
                update(newUserInput);
                update(newUserOutput);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}