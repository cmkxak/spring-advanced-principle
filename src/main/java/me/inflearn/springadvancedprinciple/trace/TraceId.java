package me.inflearn.springadvancedprinciple.trace;

import java.util.UUID;

public class TraceId {

    private String id; //트랜잭션 ID (db트랜잭션 아님)
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        //UUID의 앞 8자리만 사용
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level -1);
    }

    public boolean isFirstLevel() {
        return level == 0; //첫번째 레벨인가?
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
