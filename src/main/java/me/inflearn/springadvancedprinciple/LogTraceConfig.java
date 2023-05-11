package me.inflearn.springadvancedprinciple;

import me.inflearn.springadvancedprinciple.trace.logtrace.FieldlogTrace;
import me.inflearn.springadvancedprinciple.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new FieldlogTrace();
    }
}
