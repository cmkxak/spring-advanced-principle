package me.inflearn.springadvancedprinciple.trace;

import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV2;
import org.junit.jupiter.api.Test;

public class HelloTraceV2Test {

    @Test
    void begin_end() {
        HelloTraceV2 traceV2 = new HelloTraceV2();
        TraceStatus status = traceV2.begin("hello1");
        TraceStatus status2 = traceV2.beginSync(status.getTraceId(), "hello2");
        traceV2.end(status2);
        traceV2.end(status);
    }

    @Test
    void begin_exception() {
        HelloTraceV2 traceV2 = new HelloTraceV2();
        TraceStatus status = traceV2.begin("hello2");
        TraceStatus status2 = traceV2.beginSync(status.getTraceId(), "hello2");
        traceV2.exception(status2, new IllegalStateException());
        traceV2.exception(status, new IllegalStateException());
    }

}
