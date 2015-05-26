import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Created by torrentglenn on 5/23/15.
 */
public class MinerNode {
    //A little node that exists alongside a BFGminer
    //issues all sorts of commands to local BFGminer as commanded by its master
    public Proxy masterProxy;
    public ProxyEndPoint masterHook;
    public String ip;
    public String id = null;
    public String masterIP;

    public MinerNode(){
        //just prepares its endpoint to be hit and starts its
        //listener/handler
        try {
            masterHook = new ProxyEndPoint(this,Constants.PROXY_PORT);
            masterHook.start(Executors.newCachedThreadPool());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMasterAddr(){
        return masterIP;
    }
    public String getId(){
        return id;
    }

    public void changeId(String s){
        id = s;
    }

    public void setId(String s){
        if (id == null)
            id = s;
    }

    public String getIP(){
        return Constants.getIp();
    }

    public void print(String s){
        System.out.println(s);
    }

    public void BFGstart() throws IOException {
        //starts a local BFGminer
        System.out.println("starting?");
        Process p = Runtime.getRuntime().exec("sudo /home/tglenn/bitcoinstuff/bfgminer/bfgminer -o 10.10.117.102:9013 -u rpcfox -p foxfoxfox --generate-to 13WLBuVMEZRCW7jbxgGyZY41to6VhR6dFf --algo auto --cpu-threads 1 --api-listen -S auto");

        System.out.println("rawr");
    }


    public String BFGcommand(String cmd) throws IOException {
        //issues a command to the local BFGminer
        //takes a string which it delivers immediately to the miner
        //this method returns the BFGminer string response
        Socket localMiner = new Socket("localhost",Constants.MINER_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(localMiner.getInputStream()));
        PrintStream output = new PrintStream(localMiner.getOutputStream());
        output.println(cmd);
        return input.readLine();
    }

    public void BFGfire(String cmd) throws IOException {
        //issue a BFGminer command as a string, ignores return
        Socket localMiner = new Socket("localhost",Constants.MINER_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(localMiner.getInputStream()));
        PrintStream output = new PrintStream(localMiner.getOutputStream());
        output.println(cmd);
        localMiner.close();
    }

    public void setMasterIP(String masterIP){
        this.masterIP = masterIP;
    }

}
