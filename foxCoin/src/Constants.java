import java.net.*;
import java.util.*;
/**
 * Created by torrentglenn on 5/21/15.
 */
public class Constants {
    public static final int PROXY_PORT = 9014;
    public static final int MINER_PORT = 4038;


    public static String getIp(){
        //temporary getIP fix
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while(net.hasMoreElements()){
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()){
                InetAddress ip = addresses.nextElement();
                if (ip instanceof Inet4Address){

                    if (ip.isSiteLocalAddress()){

                        ipAddress = ip.getHostAddress();
                    }

                }

            }
        }
        return ipAddress;
    }


}
