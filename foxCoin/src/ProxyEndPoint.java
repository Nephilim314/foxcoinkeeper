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
    public Map<String,Method> methodMap = new HashMap<String, Method>();
    public Object that;
    public ServerSocket listener;
    public Socket proxySocket;
    public int port;
    public ObjectInputStream input;
    public ObjectOutputStream output;
    public ArrayList<Request> reqQueue = new ArrayList<Request>();
    public HashMap<String,Response> respMap = new HashMap<String, Response>();

    public ProxyEndPoint(Object owner, int port) throws IOException {
        that = owner;
        this.port = port;
        //proxySocket = new Socket(MIP, Constants.PROXY_PORT);
        for (Method m : owner.getClass().getMethods()){
            methodMap.put(m.getName(),m);
        }

    }

    public void start(ExecutorService ex){
        System.out.println("starting!");
        ex.execute(this);
    }

    public void run(){
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
        proxySocket = listener.accept();
       // System.out.println("Accepted");
        InputStream i = proxySocket.getInputStream();
        BufferedInputStream j = new BufferedInputStream(i);
        input = new ObjectInputStream(j);
        Object o = input.readObject();
        handle((Request) o);
        //output = new ObjectOutputStream(new BufferedOutputStream(proxySocket.getOutputStream()));
        //execute((Command) o);
    }

    private void handle(Request r) throws IOException {
      //  System.out.println("handling " + r.getType());
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
        if(reqQueue.isEmpty())
            return;

      //  System.out.println("EXECUTING");
        Request req = reqQueue.get(0);
        reqQueue.remove(0);
        Object o = null;
        Command cmd = (Command) req.getContents();
        try {
      //      System.out.println(cmd.method);
      //      for (int i = 0; i < cmd.args.length ; i++){
       //         System.out.println(cmd.args[i]);
        //    }
       //     System.out.println(methodMap.get(cmd.method));
            o = methodMap.get(cmd.method).invoke(that,cmd.args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (o != null && req.notVoid()){
         //   System.out.println("adding return val" + o);
            respMap.put(req.getId(), new Response(o));
            //try {

                //output.writeObject(new Response(o));
                //output.close();
            //} catch (IOException e) {
            //    e.printStackTrace();
           // }
       }
    }
}
