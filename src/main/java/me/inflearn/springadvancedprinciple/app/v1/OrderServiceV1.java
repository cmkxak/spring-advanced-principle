package me.inflearn.springadvancedprinciple.app.v1;

import lombok.RequiredArgsConstructor;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV1;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 traceV1;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = traceV1.begin("OrderServiceV1.orderItem()");
            orderRepository.save(itemId); //비즈니스 로직
            traceV1.end(status);
        } catch (Exception e) {
            traceV1.exception(status, e);
            throw e;
        }
    }
}
