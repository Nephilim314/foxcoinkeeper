import java.io.IOException;
import java.util.*;

/**
 * Created by torrentglenn on 5/23/15.
 */
public class INode {
    public LinkedHashMap<String,Proxy> masters = new LinkedHashMap<String, Proxy>();
    public String firstMasterIp;
    public Proxy contactMaster;
    public int proxyPort;

    public INode(String firstMaster, int port) throws IOException, ClassNotFoundException {
        proxyPort = port;
        firstMasterIp = firstMaster;
        contactMaster = new Proxy(firstMaster,proxyPort);

        String[] s = getAllMasterIps(contactMaster);
        for(String ip: s){
            if (!masters.containsKey(ip)){
                masters.put(ip,new Proxy(ip,proxyPort));
            }
        }
    }



    public String[] getAllMasterIps(Proxy m) throws IOException, ClassNotFoundException {
        String reqId = m.post(new Command("getAllMasterIps"));
        while (!m.status(reqId)){
            Thread.yield();
        }
        String[] s = (String[]) m.get(reqId).getContents();
        return s;
    }

}
