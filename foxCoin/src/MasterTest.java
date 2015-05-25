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

            LinkedHashMap<String, String> ss = m.postAll(new Command("getId"));
            LinkedHashMap<String, Response> sr = new LinkedHashMap<String, Response>();
            while (ss.size() > 0){
                sr.putAll(m.getAll(ss));
            }

            for (Map.Entry entry: sr.entrySet()){
                Response r = (Response) entry.getValue();
                System.out.println(r.getContents());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
