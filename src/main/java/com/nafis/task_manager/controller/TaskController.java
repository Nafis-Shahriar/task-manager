package com.nafis.task_manager.controller;

import com.nafis.task_manager.dto.Task;
import com.nafis.task_manager.service.TaskRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskRetriever taskRetriever;

    @GetMapping("getTask/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {

        final var task = taskRetriever.getById(id);

        return ResponseEntity.ok(task);
    }
}
