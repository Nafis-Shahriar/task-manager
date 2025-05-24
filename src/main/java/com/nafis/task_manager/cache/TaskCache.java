package com.nafis.task_manager.cache;

import com.nafis.task_manager.dto.Task;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class TaskCache {

    private static final int MAX_SIZE = 5;

    private final LRUCache<String, Task> cache = new LRUCache<>(MAX_SIZE);

    public Optional<Task> getTask(String taskId) {

        checkArgument(!taskId.isEmpty(), "taskId cannot be empty");

        return Optional.ofNullable(cache.get(taskId));
    }

    public void putTask(String taskId, Task task) {

        checkArgument(!taskId.isEmpty(), "taskId cannot be empty");
        checkNotNull(task, "task cannot be null");

        cache.put(taskId, task);
    }
}
