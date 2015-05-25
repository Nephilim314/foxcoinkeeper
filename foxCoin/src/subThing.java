import java.io.IOException;

/**
 * Created by torrentglenn on 5/21/15.
 */
public class subThing{
    public String roar(){
        return str;
    }

    public void doThing(String in){
        System.out.println(in);
    }

    public String slp(int secs) {

        try {
            Thread.sleep(secs*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "I slept " + Integer.toString(secs);
    }

    public int sum(int ... i){
        int j = 0;
        for (int a: i){
            j+=a;
        }
        System.out.println(j);
        return j;
    }

    public subThing(String s){
        str = s;
        try {
            ep = new ProxyEndPoint(this, Constants.PROXY_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ProxyEndPoint ep;
    public String str;
}