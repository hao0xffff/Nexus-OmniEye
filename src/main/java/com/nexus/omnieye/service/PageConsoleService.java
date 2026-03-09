package com.nexus.omnieye.service;

import java.util.List;

public interface PageConsoleService {
    List<String> readConsoleByUrl(String url);

    Object executeConsoleByUrl(String url, String script);
}
