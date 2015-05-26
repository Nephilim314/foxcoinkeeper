import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.net.*;
import java.util.concurrent.ExecutorService;

/**
 * Created by torrentglenn on 5/21/15.
 */
public class ProxyEndPoint implements Runnable{
    //Although called a proxy endpoint, it's really more of a message box
    //this object allows you to send commands over the network
    //to an object and optionally retrieve return values from called functions
    public Map<String,Method> methodMap = new HashMap<String, Method>(); //this is the secret method map
    public Object that;
    public ServerSocket listener;
    public Socket proxySocket;
    public int port;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    public ArrayList<Request> reqQueue = new ArrayList<Request>(); //the queue of
    public HashMap<String,Response> respMap = new HashMap<String, Response>();

    public ProxyEndPoint(Object owner, int port) throws IOException {
        that = owner;
        this.port = port;

        //this makes a map of strings to PUBLIC methods
        //allowing them to be accessed from over the network
        for (Method m : owner.getClass().getMethods()){
            methodMap.put(m.getName(),m);
        }

    }

    public void start(ExecutorService ex){
        //this starts the proxyEndpoint listening for messages in a
        //different thread. This process only accepts messages and
        //updates the
        System.out.println("starting!");
        ex.execute(this);
    }

    public void run(){
        //the actual run function, called in the new thread
        System.out.println("RUNNING!");
        try {
            listen(port);
            for(;;){
                accept();
                //Thread.yield();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void listen(int port) throws IOException {
        listener = new ServerSocket(port);
    }

    public void accept() throws IOException, ClassNotFoundException {
        //accept a connection and handle the request
        proxySocket = listener.accept();
        InputStream i = proxySocket.getInputStream();
        BufferedInputStream j = new BufferedInputStream(i);
        input = new ObjectInputStream(j);
        Object o = input.readObject();
        handle((Request) o);
        //output = new ObjectOutputStream(new BufferedOutputStream(proxySocket.getOutputStream()));
        //execute((Command) o);
    }

    private void handle(Request r) throws IOException {
        //Handle the various requests based on the requests type field
        switch (r.getType()){
            case STATUS: {
                output = new ObjectOutputStream(new BufferedOutputStream(proxySocket.getOutputStream()));

                if (respMap.containsKey(r.getId()))
                    output.writeObject(new Response(true));
                else
                    output.writeObject(new Response(false));
                output.flush();
                output.close();
                break;
            }

            case GET:{
                output = new ObjectOutputStream(new BufferedOutputStream(proxySocket.getOutputStream()));
                output.writeObject(respMap.remove(r.getId()));
                output.flush();
                output.close();
                break;
            }

            case POST:{
                reqQueue.add(r);
                break;
            }
        }
    }



    public void execute(){
        //Method for executing the next command from the message queue
        //if a return value has been requested, make a response and add it
        //to the outbox (respMap) queue
        if(reqQueue.isEmpty())
            return;

        Request req = reqQueue.get(0);
        reqQueue.remove(0);
        Object o = null;
        Command cmd = (Command) req.getContents();
        try {
            o = methodMap.get(cmd.method).invoke(that,cmd.args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (o != null && req.notVoid()){
            respMap.put(req.getId(), new Response(o));
       }
    }
}
