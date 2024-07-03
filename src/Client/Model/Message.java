package Client.Model;

import java.util.*;

/**
 * 
 */
public class Message {

    /**
     * Default constructor
     */
    public Message(String text,String clock) {
        this.text=text;
        this.clock=clock;
    }

    /**
     * 
     */
    private String text;

    /**
     * 
     */
    private String clock;

    public String getText() {
        return text;
    }

    public String getClock() {
        return clock;
    }
}