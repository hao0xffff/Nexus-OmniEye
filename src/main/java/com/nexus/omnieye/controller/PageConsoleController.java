package com.nexus.omnieye.controller;

import com.nexus.omnieye.controller.dto.ConsoleExecuteRequest;
import com.nexus.omnieye.controller.dto.ConsoleExecuteResponse;
import com.nexus.omnieye.service.PageConsoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pages/console")
public class PageConsoleController {
    private final PageConsoleService pageConsoleService;

    public PageConsoleController(PageConsoleService pageConsoleService) {
        this.pageConsoleService = pageConsoleService;
    }

    @GetMapping
    public ResponseEntity<?> readConsole(@RequestParam("url") String url) {
        try {
            List<String> logs = pageConsoleService.readConsoleByUrl(url);
            return ResponseEntity.ok(logs);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/execute")
    public ResponseEntity<?> executeConsole(@RequestBody ConsoleExecuteRequest request) {
        try {
            Object result = pageConsoleService.executeConsoleByUrl(request.url(), request.script());
            return ResponseEntity.ok(new ConsoleExecuteResponse(request.url(), result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
