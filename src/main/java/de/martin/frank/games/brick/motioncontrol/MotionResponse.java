package de.martin.frank.games.brick.motioncontrol;

public class MotionResponse {

    private final long id;
    private final String content;

    public MotionResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    ;

    private enum MotionControlType {CATERPILLAR, WHEEL}

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
