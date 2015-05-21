import java.io.IOException;

/**
 * Created by torrentglenn on 5/21/15.
 */
public class subThing{
    public String roar(){
        System.out.println("YES");
        return str;
    }

    public int sum(int ... i){
        int j = 0;
        for (int a: i){
            j+=a;
        }
        return j;
    }

    public subThing(String s){
        str = s;
        try {
            ep = new ProxyEndPoint(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ProxyEndPoint ep;
    public String str;
}