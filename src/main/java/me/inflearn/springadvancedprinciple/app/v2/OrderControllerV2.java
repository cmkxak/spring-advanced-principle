package me.inflearn.springadvancedprinciple.app.v2;

import lombok.RequiredArgsConstructor;
import me.inflearn.springadvancedprinciple.trace.TraceStatus;
import me.inflearn.springadvancedprinciple.trace.hellotrace.HelloTraceV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {
        TraceStatus status = trace.begin("OrderControllerV2.request()");
        try {
            orderService.orderItem(itemId, status.getTraceId());
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져줘야 한다.
        }
    }
}
