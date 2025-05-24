package com.nafis.task_manager.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nafis.task_manager.cache.TaskCache;
import com.nafis.task_manager.configuration.AppProperties;
import com.nafis.task_manager.dto.Task;
import com.nafis.task_manager.exception.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskRetriever {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AppProperties appProperties;

    private final TaskCache taskCache = new TaskCache();

    public Task getById(String id) {

        checkArgument(!id.isEmpty(), "Task id cannot be empty");

        return taskCache
                .getTask(id)
                .orElseGet(() -> fetchAndCacheTask(id));
    }

    private Task fetchAndCacheTask(String id) {

        log.info("Task not found in cache. Fetching from file directory.");

        return fetchFromFileDirectory(id)
                .map(task -> {
                    taskCache.putTask(id, task);
                    return task;
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    private Optional<Task> fetchFromFileDirectory(String id) {

        final var directory = appProperties.getRootDirectory() + appProperties.getTaskFolder();

        log.info("Fetching task from file directory - {}", directory);

        Path filePath = Path.of(directory, id + ".json");

        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {

            log.error("Task with id {} not found", id);

            throw new TaskNotFoundException(id);
        }

        try {

            final var task = objectMapper.readValue(file, Task.class);

            return Optional.of(task);

        } catch (JsonProcessingException e) {

            log.error("Failed to parse json", e);

            throw new RuntimeException("Malformed json", e);

        } catch (Exception e) {

            log.error("Failed to retrieve task", e);

            throw new InternalException("Failed to retrieve task", e);
        }
    }
}
