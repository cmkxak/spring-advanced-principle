package me.inflearn.springadvancedprinciple.trace.logtrace;

import me.inflearn.springadvancedprinciple.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
