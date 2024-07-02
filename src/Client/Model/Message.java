package Client.Model;

import java.util.*;

/**
 * 
 */
public class Message {

    /**
     * Default constructor
     */
    public Message(String text,Vector<String> clockState) {
        this.text=text;
        this.clockState=clockState;
    }

    /**
     * 
     */
    private String text;

    /**
     * 
     */
    private Vector clockState;

    public String getText() {
        return text;
    }

    public Vector getClockState() {
        return clockState;
    }
}