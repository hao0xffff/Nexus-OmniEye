package com.nexus.omnieye.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsoleLog {
    private String type;          // LOG, WARN, ERROR
    private String text;          // 日志内容
    private LocalDateTime time;   // 感知到信号的时间
    private String sourceUrl;     // 产生该日志的 URL 来源
}