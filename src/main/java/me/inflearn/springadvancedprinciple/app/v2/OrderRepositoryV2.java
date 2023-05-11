package me.inflearn.springadvancedprinciple.app.v2;

import lombok.RequiredArgsConstructor;
import me.inflearn.springadvancedprinciple.trace.TraceId;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV2 {
    private final HelloTraceV2 trace;

    public void save(String itemId, TraceId traceId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId,"orderRepositoryV2.save()");
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000); //상품을 저장하는데 1초간 걸린다고 가정
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
