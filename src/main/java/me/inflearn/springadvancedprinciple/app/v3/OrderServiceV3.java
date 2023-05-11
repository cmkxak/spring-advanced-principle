package me.inflearn.springadvancedprinciple.app.v3;

import lombok.RequiredArgsConstructor;
import me.inflearn.springadvancedprinciple.LogTraceConfig;
import me.inflearn.springadvancedprinciple.trace.TraceId;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV2;
import me.inflearn.springadvancedprinciple.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderServiceV2.orderItem()");
            orderRepository.save(itemId); //비즈니스 로직
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
