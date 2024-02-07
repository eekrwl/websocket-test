package com.example.websocket.repository;

import com.example.websocket.entity.Parent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParentRepository {

    private static ParentRepository parentRepository = new ParentRepository();
    private final Map<String, Parent> parents = new ConcurrentHashMap();

    private ParentRepository() {
    }

    public static ParentRepository getInstance() {
        return parentRepository;
    }

    public void create(Parent parent) {
        parent.setDtype("parent");
        parents.put(parent.getId(), parent);
    }

    public Parent findById(String id) {
        return parents.get(id);
    }
}
