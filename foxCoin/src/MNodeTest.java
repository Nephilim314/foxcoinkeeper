import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by torrentglenn on 5/25/15.
 */
public class MNodeTest {
    public static void main(String[] args){
        MasterNode m = new MasterNode("fox1");
        System.out.println(m.getIP());
        try {
            for(String s : args){
                m.addMiner(s,Constants.PROXY_PORT,m.getId()+Long.toString(new Date().getTime()));
            }
            for(Map.Entry<String,Proxy> entry: m.miners.entrySet()){
                entry.getValue().fire(new Command("print","FOXES RUL"));
            }

            LinkedHashMap<String, String> ss = m.postAll(new Command("getIP"));
            LinkedHashMap<String, Response> sr = new LinkedHashMap<String, Response>();
            while (ss.size() > 0){
                sr.putAll(m.getAll(ss));
            }

            for (Map.Entry entry: sr.entrySet()){
                Response r = (Response) entry.getValue();
                System.out.println((String)entry.getKey() +" "+ r.getContents());
            }

            for(;;) {
                m.remoteQueue.execute();
                Thread.yield();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
