package me.inflearn.springadvancedprinciple.app.v2;

import lombok.RequiredArgsConstructor;
import me.inflearn.springadvancedprinciple.trace.TraceId;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(String itemId, TraceId traceId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId,"OrderServiceV2.orderItem()");
            orderRepository.save(itemId, status.getTraceId()); //비즈니스 로직
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
