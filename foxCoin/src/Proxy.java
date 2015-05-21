import java.io.*;
import java.net.Socket;

/**
 * Created by torrentglenn on 5/21/15.
 */
public class Proxy {
    public String endPointIp;
    public int port;
    public Socket epSocket;
    public ObjectInputStream input;
    public ObjectOutputStream output;

    public Proxy(String endPtIp, int port){
        this.port = port;
        endPointIp = endPtIp;
    }

    public void setPort(int p){
        port = p;
    }

    public void setEndPointIp(String ep){
        endPointIp = ep;
    }

    public Object invoke(String method, Object ... args) throws IOException, ClassNotFoundException {
        epSocket = new Socket(endPointIp,port);
        output = new ObjectOutputStream(new BufferedOutputStream(epSocket.getOutputStream()));
        output.writeObject(new Command(method,args));
        output.flush();
       // output.close();
        input = new ObjectInputStream(new BufferedInputStream(epSocket.getInputStream()));
        Object o = input.readObject();
        epSocket.close();
        return o;
    }
}
