package com.nexus.omnieye;

import com.nexus.omnieye.core.OmniContext;
import com.nexus.omnieye.service.BrowserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NexusOmniEyeApplication {

    public static void main(String[] args) {
        // 确保启动类名与当前类名一致
        SpringApplication.run(NexusOmniEyeApplication.class, args);
    }

    @Bean
    public CommandLineRunner runDemo(BrowserService browserService) {
        return args -> {
            System.out.println("\n[Nexus-CLI] 启动防御性链路测试...");

            // 策略修改：先访问 about:blank 确保浏览器环境稳定
            OmniContext context = browserService.createContext("about:blank");

            // 稍微等待环境稳定
            Thread.sleep(1000);

            // 执行指令
            Object result = context.execute("console.log('Nexus-CLI: Local Shield Active')");
            System.out.println(">> 指令执行结果: " + result);

            // 尝试一个可能失败的操作，看它是否还会崩掉程序
            context.execute("window.location.href = 'https://www.google.com'");
            // 紧接着执行，利用刚才增加的 try-catch 保护程序
            Object errorTest = context.execute("console.log('这行可能在跳转中执行')");
            System.out.println(">> 防御性执行测试: " + errorTest);

            Thread.sleep(2000);
            System.out.println("\n--- 感知日志清单 ---");
            context.fetchLogs().forEach(System.out::println);
        };
    }
}