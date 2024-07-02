package Client.Model;

import java.util.*;

/**
 * 
 */
public class Message {

    /**
     * Default constructor
     */
    public Message(String text,ArrayList<String> clockState) {
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
    private ArrayList clockState;

    public String getText() {
        return text;
    }

    public ArrayList getClockState() {
        return clockState;
    }
}