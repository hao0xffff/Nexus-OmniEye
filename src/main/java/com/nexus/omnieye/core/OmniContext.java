package com.nexus.omnieye.core;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

public class OmniContext {
    @Getter
    private final String url;
    private final Page page;
    private final List<String> logs = new ArrayList<>();

    public OmniContext(String url, Page page) {
        this.url = url;
        this.page = page;
        setupConsoleListener();
    }

    private void setupConsoleListener() {
        page.onConsoleMessage(msg -> {
            synchronized (logs) {
                logs.add(String.format("[%s] %s", msg.type().toUpperCase(), msg.text()));
            }
        });
    }

    /**
     * 强力执行接口：捕获上下文销毁异常
     */
    public Object execute(String script) {
        try {
            // 增加安全检查：确保页面没关掉
            if (page.isClosed()) return "Error: Page is closed";

            System.out.println("<< [Context: " + url + "] 执行指令: " + script);
            return page.evaluate(script);
        } catch (PlaywrightException e) {
            // 专门处理上下文销毁问题
            if (e.getMessage().contains("Execution context was destroyed")) {
                return "Execution Failed: Context destroyed (page navigating?)";
            }
            return "Execution Error: " + e.getMessage();
        }
    }

    public List<String> fetchLogs() {
        synchronized (logs) {
            return new ArrayList<>(logs);
        }
    }
}