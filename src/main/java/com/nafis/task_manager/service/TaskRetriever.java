package com.nafis.task_manager.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nafis.task_manager.dto.Task;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
public class TaskRetriever {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${task.folder.path:/home/nafis/Documents}")
    private String taskFolderPath;

    public Optional<Task> getById(String id) {

        final var folder = "/Tasks";

        Path filePath = Path.of(taskFolderPath + folder, id + ".json");
        File file = filePath.toFile();

        if (!file.exists() || !file.isFile()) {
            return Optional.empty();
        }

        try {
            Task task = objectMapper.readValue(file, Task.class);
            return Optional.ofNullable(task);

        } catch (JsonProcessingException e) {

            log.error("Failed to parse task", e);
            throw new RuntimeException("Malformed json", e);

        } catch (Exception e) {
            throw new InternalException("Failed to retrieve task", e);
        }
    }
}
