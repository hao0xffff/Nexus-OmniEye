package com.nexus.omnieye.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PageState {
    private String url;
    private String title;
    private List<ConsoleLog> recentLogs;
    private boolean isLoaded;
}