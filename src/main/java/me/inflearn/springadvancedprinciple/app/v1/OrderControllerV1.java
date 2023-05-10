package me.inflearn.springadvancedprinciple.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;

    @GetMapping("/v1/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }
}
