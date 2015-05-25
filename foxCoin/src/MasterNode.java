import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by torrentglenn on 5/23/15.
 */
public class MasterNode {
    public ProxyEndPoint remoteQueue;
    public String IP;
    public List<Proxy> masters = new LinkedList<Proxy>();
    public List<Proxy> miners = new LinkedList<Proxy>(); //might need to be a Map<uniqueID,minerProxy>

    public void addMiner(String IP, int port, String uid) throws IOException {
        Proxy miner = new Proxy(IP,port);
        miner.fire(new Command("setMasterIP",IP));
        miner.fire(new Command("setId",uid));
        miners.add(miner);
    };

    public String getIP(){
        return Constants.getIp();
    }





    public void fireAllMiners(Command cmd) throws IOException {
        for (Proxy miner: miners){
            miner.fire(cmd);
        }
    }


}
