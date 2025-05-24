package com.nafis.task_manager.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nafis.task_manager.dto.Task;
import com.nafis.task_manager.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;

@Slf4j
@Service
public class TaskRetriever {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${task.folder.path:/home/nafis/Documents}")
    private String taskFolderPath;

    public Task getById(String id) {

        final var folder = "/Tasks";

        Path filePath = Path.of(taskFolderPath + folder, id + ".json");
        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {

            log.error("Task with id {} not found", id);

            throw new TaskNotFoundException("Task not found with ID: " + id);
        }

        try {

            return objectMapper.readValue(file, Task.class);

        } catch (JsonProcessingException e) {

            log.error("Failed to parse json", e);

            throw new RuntimeException("Malformed json", e);

        } catch (Exception e) {

            log.error("Failed to retrieve task", e);

            throw new InternalException("Failed to retrieve task", e);
        }
    }
}
