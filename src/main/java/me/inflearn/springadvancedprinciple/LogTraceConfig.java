package me.inflearn.springadvancedprinciple;

import me.inflearn.springadvancedprinciple.trace.logtrace.LogTrace;
import me.inflearn.springadvancedprinciple.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
