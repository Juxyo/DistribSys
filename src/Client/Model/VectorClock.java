package Client.Model;

import java.util.*;

/**
 * 
 */
public class VectorClock {

    /**
     * 
     */
    private ArrayList<String> clockState=new ArrayList<>();

    /**
     * Default constructor
     */
    public VectorClock() {
        clockState.add("0");
    }

    /**
     * returns true if the first clock is the oldest
     */
    public static boolean clockCompare(String clock1,String clock2) {
        String[] c1=clock1.split(",");
        String[] c2=clock2.split(",");
        int result=0;
        if(c1.length==c2.length){
            for (int i = 0; i < c1.length; i++) {
                int i1=Integer.parseInt(c1[i]);
                int i2=Integer.parseInt(c2[i]);
                if(i1<i2)result++;
                else if (i1>i2) result--;
            }
        } else if (c1.length<c2.length) {
            return true;
        }
        if(result<0) return false;
        else return true;
    }

    /**
     * 
     */
    public void increment() {
        int val=Integer.parseInt(clockState.get(0))+1;
        clockState.set(0,""+val);
    }

    /**
     *
     */
    public void addClock(String value) {
        clockState.add(value);
    }

    /**
     *
     */
    public void setClock(int hostIndex,String value) {
        clockState.set(hostIndex,value);
    }

    /**
     *
     */
    public void removeClock(int hostIndex) {
        clockState.remove(hostIndex);
    }

    @Override
    public String toString() {
        String ret="";
        for (int i = 0; i < clockState.size(); i++) {
            if(i==clockState.size()-1) ret+=clockState.get(i);
            else ret+=clockState.get(i)+",";
        }
        return ret;
    }
}