package com.nafis.task_manager.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Task {

    private UUID id;

    private String description;
}
