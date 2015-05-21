import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.net.*;
/**
 * Created by torrentglenn on 5/21/15.
 */
public class ProxyEndPoint {
    public Map<String,Method> methodMap = new HashMap<String, Method>();
    public Object that;
    public ServerSocket listener;
    public Socket proxySocket;
    public ObjectInputStream input;
    public ObjectOutputStream output;

    public ProxyEndPoint(Object owner) throws IOException {
        that = owner;
        //proxySocket = new Socket(MIP, Constants.PROXY_PORT);
        for (Method m : owner.getClass().getMethods()){
            methodMap.put(m.getName(),m);
        }
    }

    public void listen(int port) throws IOException {
        listener = new ServerSocket(port);
    }

    public void accept() throws IOException, ClassNotFoundException {
        proxySocket = listener.accept();
        InputStream i = proxySocket.getInputStream();
        BufferedInputStream j = new BufferedInputStream(i);
        input = new ObjectInputStream(j);
        Object o = input.readObject();
        output = new ObjectOutputStream(new BufferedOutputStream(proxySocket.getOutputStream()));
        execute((Command) o);
    }

    public void execute(Command cmd){
        //Presently fails to work on methods w/ variable arguments?
        Object o = null;
        try {
            System.out.println(cmd.method);
            for (int i = 0; i < cmd.args.length ; i++){
                System.out.println(cmd.args[i]);
            }
            System.out.println(methodMap.get(cmd.method));
            o = methodMap.get(cmd.method).invoke(that,cmd.args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if (o != null){
            try {
                output.writeObject(new Response(o));
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
       }
    }
}
