package com.nafis.task_manager.cache;

import com.nafis.task_manager.dto.Task;

import java.util.Optional;

public class TaskCache {

    private static final int MAX_SIZE = 5;

    private final LRUCache<String, Task> cache = new LRUCache<>(MAX_SIZE);

    public Optional<Task> getTask(String taskId) {
        return Optional.ofNullable(cache.get(taskId));
    }

    public void putTask(String taskId, Task task) {
        cache.put(taskId, task);
    }
}
