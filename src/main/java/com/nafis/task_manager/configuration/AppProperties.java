package com.nafis.task_manager.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppProperties {

    @Value("${root.directory}")
    private String rootDirectory;

    @Value("${tasks.folder.name}")
    private String taskFolder;

    @Value("${max.cache.size}")
    private int maxCacheSize;
}
