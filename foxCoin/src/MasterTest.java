import java.io.IOException;
import java.util.*;
/**
 * Created by torrentglenn on 5/25/15.
 */
public class MasterTest {
    public static void main(String[] args){
        MasterNode m = new MasterNode("fox1");
        try {
            for(String s : args){
                m.addMiner(s,Constants.PROXY_PORT,m.getId()+Long.toString(new Date().getTime()));
            }
            for(Map.Entry<String,Proxy> entry: m.miners.entrySet()){
                entry.getValue().fire(new Command("print","FOXES RUL"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
