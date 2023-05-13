package me.inflearn.springadvancedprinciple.trace.threadlocal;

import lombok.extern.slf4j.Slf4j;
import me.inflearn.springadvancedprinciple.trace.threadlocal.code.FieldService;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("메인 스레드 시작");
        Runnable userA = () -> fieldService.logic("userA");

        Runnable userB = () -> fieldService.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(2000); //동시성 문제 발생 x
        threadB.start();
        sleep(2000); //메인 스레드 종료 대기
        log.info("메인 스레드 종료");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
