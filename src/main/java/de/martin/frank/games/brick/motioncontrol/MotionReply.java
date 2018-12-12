package de.martin.frank.games.brick.motioncontrol;

public class MotionReply {

    private final long id;
    private final String content;

    public MotionReply(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
