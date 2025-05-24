package com.nafis.task_manager.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String id) {
        super("Task not found with ID: " + id);
    }
}
