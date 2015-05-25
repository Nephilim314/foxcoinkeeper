import java.io.IOException;
import java.util.*;

/**
 * Created by torrentglenn on 5/23/15.
 */
public class MasterNode {
    public ProxyEndPoint remoteQueue;
    public String IP;
    public String id;
    public List<Proxy> masters = new LinkedList<Proxy>();
    public Map<String,Proxy> miners = new HashMap<String,Proxy>(); //might need to be a Map<uniqueID,minerProxy>

    public MasterNode(String id){
        try {
            remoteQueue = new ProxyEndPoint(this, Constants.PROXY_PORT);
            this.id = id;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMiner(String IP, int port, String uid) throws IOException {
        Proxy miner = new Proxy(IP,port);
        miner.fire(new Command("setMasterIP",IP));
        miner.fire(new Command("setId",uid));
        miners.put(uid, miner);
    };

    public String getId(){
        return id;
    }
    public String getIP(){
        return Constants.getIp();
    }





    public void fireAllMiners(Command cmd) throws IOException {
        for (String minerID: miners.keySet()){
            miners.get(minerID).fire(cmd);
        }
    }


}
