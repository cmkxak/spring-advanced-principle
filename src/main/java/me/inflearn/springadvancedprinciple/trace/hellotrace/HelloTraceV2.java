package me.inflearn.springadvancedprinciple.trace.hellotrace;

import lombok.extern.slf4j.Slf4j;
import me.inflearn.springadvancedprinciple.trace.TraceId;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETION_PREFIX = "<--";
    private static final String EXCEPTION_PREFIX = "<x-";

    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {} {}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    //V2에서 추가
    //기존 traceId에서 createNextId를 통해 다음 아이디를 구한다.
    //트랜잭션 아이디는 기존과 같이 그대로 유지, level은 하나 증가
    public TraceStatus beginSync(TraceId beforeTraceId, String message) {
//        TraceId traceId = new TraceId();
        TraceId nextId = beforeTraceId.createNextId(); //level만 1 wmdrk
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {} {}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
        return new TraceStatus(nextId, startTimeMs, message);
    }

    public void end(TraceStatus status) {
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        long resultTimeMs = System.currentTimeMillis() - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if (e == null) {
            //정상 종료 로그 출력
            log.info("[{}] {} {} time = {}ms", traceId.getId(), addSpace(COMPLETION_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            //예외 발생 로그 출력
            log.info("[{}] {} {} time = {}ms", traceId.getId(), addSpace(EXCEPTION_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        }

    }

    private Object addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();

    }

}

