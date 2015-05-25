import java.io.*;
import java.net.Socket;
import java.util.Date;
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

    public String post(Command cmd) throws IOException {
        epSocket = new Socket(endPointIp,port);
        output = new ObjectOutputStream(new BufferedOutputStream(epSocket.getOutputStream()));
        Request r = Request.PostRequest(Long.toString(new Date().getTime())+cmd.method, cmd, true);
        output.writeObject(r);
        output.flush();
        epSocket.close();
        return r.getId();
    }

    public void fire(Command cmd) throws IOException {
        epSocket = new Socket(endPointIp,port);
        output = new ObjectOutputStream(new BufferedOutputStream(epSocket.getOutputStream()));
        Request r = Request.PostRequest(Long.toString(new Date().getTime())+cmd.method, cmd, false);
        output.writeObject(r);
        output.flush();
        epSocket.close();
    }

    public boolean status(String ID) throws IOException, ClassNotFoundException {
        epSocket = new Socket(endPointIp,port);
        output = new ObjectOutputStream(new BufferedOutputStream(epSocket.getOutputStream()));
        output.writeObject(Request.StatRequest(ID));
        output.flush();
        //output.close();
        input = new ObjectInputStream(new BufferedInputStream(epSocket.getInputStream()));
        Response r = (Response) input.readObject();
        epSocket.close();
        return (Boolean) r.getContents();
    }

    public Response get(String ID) throws IOException, ClassNotFoundException {
        epSocket = new Socket(endPointIp,port);
        output = new ObjectOutputStream(new BufferedOutputStream(epSocket.getOutputStream()));
        output.writeObject(Request.GetRequest(ID));
        output.flush();
        //output.close();
        input = new ObjectInputStream(new BufferedInputStream(epSocket.getInputStream()));
        Response r = (Response) input.readObject();
        epSocket.close();
        return r;
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
