import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by torrentglenn on 5/25/15.
 */
public class INodeTest {
    public static void main(String[] args){
        try {
            INode IN = new INode(args[0],Constants.PROXY_PORT);

            String reqId;

            reqId = IN.contactMaster.post(new Command("postAll",new Command(args[1],args[2])));
            while (!IN.contactMaster.status(reqId)){
                Thread.yield();
            }
            Response r = IN.contactMaster.get(reqId);

            LinkedHashMap<String,String> ss = (LinkedHashMap<String, String>) r.getContents();

            reqId = IN.contactMaster.post(new Command("getAllSync", ss));
            while (!IN.contactMaster.status(reqId)){
                Thread.yield();
            }
            r = IN.contactMaster.get(reqId);
            LinkedHashMap<String, Response> goodies = (LinkedHashMap<String, Response>) r.getContents();

            for(Map.Entry entry: goodies.entrySet()){
                Response resp = (Response) entry.getValue();
                System.out.println((String) entry.getKey() +" "+ resp.getContents());
                System.out.println();
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
