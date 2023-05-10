package me.inflearn.springadvancedprinciple.app.v1;

import lombok.RequiredArgsConstructor;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV1;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryV1 {
    private final HelloTraceV1 traceV1;

    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = traceV1.begin("orderRepository.save()");
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000); //상품을 저장하는데 1초간 걸린다고 가정
            traceV1.end(status);
        } catch (Exception e) {
            traceV1.exception(status, e);
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
