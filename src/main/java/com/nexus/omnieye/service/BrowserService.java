package com.nexus.omnieye.service;

import com.microsoft.playwright.*;
import com.nexus.omnieye.core.OmniContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BrowserService {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;

    @PostConstruct
    public void init() {
        playwright = Playwright.create();
        // 增加 --no-proxy-server 绕过本地代理限制
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setArgs(List.of("--no-proxy-server"))
                .setHeadless(false));
        browserContext = browser.newContext();
    }

    /**
     * 输入 URL，创建一个受控的上下文对象
     */
    public OmniContext createContext(String url) {
        Page page = browserContext.newPage();
        try {
            page.navigate(url);
        } catch (Exception e) {
            System.err.println(">> [Nexus] 初始导航失败: " + e.getMessage());
        }
        return new OmniContext(url, page);
    }

    @PreDestroy
    public void stop() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}