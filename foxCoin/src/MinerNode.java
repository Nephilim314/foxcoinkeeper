import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by torrentglenn on 5/23/15.
 */
public class MinerNode {
    public Proxy masterProxy;
    public ProxyEndPoint masterHook;
    public String ip;
    public String id;
    public String masterIP;

    public MinerNode(){
        try {
            masterHook = new ProxyEndPoint(this,Constants.PROXY_PORT);

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

    public void setId(String s){
        id = s;
    }

    public String getIP(){
        return Constants.getIp();
    }

    public void print(String s){
        System.out.println(s);
    }

    public String BFGcommand(String cmd) throws IOException {
        Socket localMiner = new Socket("localhost",Constants.MINER_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(localMiner.getInputStream()));
        PrintStream output = new PrintStream(localMiner.getOutputStream());
        output.println(cmd);
        return input.readLine();
    }

    public void setMasterIP(String masterIP){
        this.masterIP = masterIP;
    }

}
