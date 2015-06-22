package au.edu.adelaide.cs.mwn;



public class TrachThread extends Thread {

    private static int threadID = 0; // shared by all
    private String path;
    
    /**
     * constructor
     */
    public TrachThread(String name,String path) {
        super(name);
        this.path = path;
    }

    /**
     * convert object to string
     */
    public String toString() {
        return super.getName();
    }

    /**
     * what does the thread do?
     */
    public void run() {
        CallTrack ct =new CallTrack();
        ct.excute(path);
    }
}