import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Created by torrentglenn on 5/23/15.
 */
public class MasterNode {
    public ProxyEndPoint remoteQueue;
    public String IP;
    public String id;
    //both maps map IP addrs to Proxy instances
    public Map<String,Proxy> masters = new LinkedHashMap<String, Proxy>();
    public Map<String,Proxy> miners = new LinkedHashMap<String, Proxy>(); //might need to be a Map<uniqueID,minerProxy>

    public MasterNode(String id){
        try {
            remoteQueue = new ProxyEndPoint(this, Constants.PROXY_PORT);
            this.id = id;
            remoteQueue.start(Executors.newCachedThreadPool());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void introduce(Proxy otherM) throws IOException {
        otherM.fire(new Command("addMaster",Constants.getIp(),Constants.PROXY_PORT));
    }

    public void addMaster(String IP, int port){
        if(IP.equals(Constants.getIp()))
            return;
        Proxy master = new Proxy(IP,port);
        masters.put(IP, master);
    }

    public String[] getAllMasterIps(){
        String[] s = new String[masters.size()];
        int i = 0;
        for(Map.Entry e: masters.entrySet()){
            s[i] = (String) e.getKey();
            i++;
        }
        return s;
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



    public LinkedHashMap<String, String> postAll(Command cmd) throws IOException {
        LinkedHashMap<String, String> m = new LinkedHashMap<String, String>();
        for (String minerID: miners.keySet()){
            m.put(minerID, miners.get(minerID).post(cmd));
        }
        return m;
    }

    public LinkedHashMap<String, Response> getAll(LinkedHashMap<String, String> idToReqId) throws IOException, ClassNotFoundException {
        LinkedHashMap<String,Response> l = new LinkedHashMap<String, Response>();
        LinkedHashMap<String,String> toRemove = new LinkedHashMap<String, String>();
        for(Map.Entry entry: idToReqId.entrySet()){
            if(miners.get(entry.getKey()).status((String) entry.getValue())) {
                l.put((String) entry.getKey(), miners.get(entry.getKey()).get((String) entry.getValue()));
                toRemove.put((String) entry.getKey(),(String) entry.getValue());
               // idToReqId.remove(entry.getKey());
            }
        }
        for(Map.Entry<String,String> entry: toRemove.entrySet()){
            if (idToReqId.containsKey(entry.getKey())){
                idToReqId.remove(entry.getKey());
            }
        }
        return l;
    }

    public void fireAll(Command cmd) throws IOException {
        for (String minerID: miners.keySet()){
            miners.get(minerID).fire(cmd);
        }
    }


    public LinkedHashMap<String, Response> getAllSync(LinkedHashMap<String,String> idToReqId) throws IOException, ClassNotFoundException {
        LinkedHashMap<String, String> ss = idToReqId;
        LinkedHashMap<String, Response> sr = new LinkedHashMap<String, Response>();
        while (ss.size() > 0){
            sr.putAll(getAll(ss));
        }
        return sr;
    }

}
