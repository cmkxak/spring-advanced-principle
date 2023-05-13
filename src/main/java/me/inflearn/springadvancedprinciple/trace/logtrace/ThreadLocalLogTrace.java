package me.inflearn.springadvancedprinciple.trace.logtrace;

import lombok.extern.slf4j.Slf4j;
import me.inflearn.springadvancedprinciple.trace.TraceId;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETION_PREFIX = "<--";
    private static final String EXCEPTION_PREFIX = "<x-";

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceId(); //동기화를 하고 나면 traceIdHolder에 값이 있다는 것이 보장됨.
        TraceId traceId = traceIdHolder.get();
        long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {} {}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            traceIdHolder.set(new TraceId());
        } else{
            traceIdHolder.set(traceId.createNextId());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
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
        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) { //traceId의 레벨이 다시 0으로 왔다는 것은 다 실행하고 마지막으로 온 것 -> 로그가 끝났다는 것
            traceIdHolder.remove();//destroy log, 쓰레드 로컬에 저장된 값을 제거
        } else{
             traceIdHolder.set(traceId.createPreviousId());//로그가 넘어오는 단계 -> 이전 아이디 반환
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
