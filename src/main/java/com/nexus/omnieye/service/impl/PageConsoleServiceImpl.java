package com.nexus.omnieye.service.impl;

import com.nexus.omnieye.core.OmniContext;
import com.nexus.omnieye.repository.PageContextRepository;
import com.nexus.omnieye.service.BrowserService;
import com.nexus.omnieye.service.PageConsoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PageConsoleServiceImpl implements PageConsoleService {
    private final PageContextRepository pageContextRepository;
    private final BrowserService browserService;

    public PageConsoleServiceImpl(PageContextRepository pageContextRepository, BrowserService browserService) {
        this.pageContextRepository = pageContextRepository;
        this.browserService = browserService;
    }

    @Override
    public List<String> readConsoleByUrl(String url) {
        OmniContext context = getOrCreateContext(url);
        return context.fetchLogs();
    }

    @Override
    public Object executeConsoleByUrl(String url, String script) {
        if (!StringUtils.hasText(script)) {
            throw new IllegalArgumentException("script must not be blank");
        }
        OmniContext context = getOrCreateContext(url);
        return context.execute(script);
    }

    private OmniContext getOrCreateContext(String url) {
        String normalizedUrl = normalizeUrl(url);
        return pageContextRepository.getOrCreate(normalizedUrl, () -> browserService.createContext(normalizedUrl));
    }

    private String normalizeUrl(String url) {
        if (!StringUtils.hasText(url)) {
            throw new IllegalArgumentException("url must not be blank");
        }
        return url.trim();
    }
}
