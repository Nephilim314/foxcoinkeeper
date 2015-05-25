import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by torrentglenn on 5/21/15.
 */





public class Rmain {
    public static void main(String[] args){
        System.out.println(Constants.getIp());
        thing t = new thing("localhost", Constants.PROXY_PORT);
//        subThing st = new subThing("RAWR");
        LinkedList<String> strings = new LinkedList<String>();
        try {
            t.subThingProxy.fire(new Command("doThing", "STARTUP ACROSS THE NET"));
              strings.add(t.subThingProxy.post(new Command("roar")));
            t.subThingProxy.fire(new Command("doThing", "ROAR"));
            strings.add(t.subThingProxy.post(new Command("slp", 4)));
              strings.add(t.subThingProxy.post(new Command("sum", new int[]{3, 4, 5})));
            t.subThingProxy.fire(new Command("doThing", "superRawr"));
            strings.add(t.subThingProxy.post(new Command("sum", new int[]{3,4,3})));

            System.out.println("Sent sum!");
        /*
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("waking up!");
    */
            String s;
            while (!strings.isEmpty()){
                s = strings.poll();
                if (t.subThingProxy.status(s)){
                    System.out.println(t.subThingProxy.get(s).getContents());
                } else {
                    strings.add(s);
                }
            }

            System.out.println("done");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



        /*
        try {
          //  st.ep.listen(Constants.PROXY_PORT);
            Response r = (Response) t.subThingProxy.invoke("slp",5);
            System.out.println(r.getContents());

            Response q = (Response) t.subThingProxy.invoke("sum", new int[]{1, 3, 5, 1,4, 5,22}); //AHA NEW ARRAY LITERAL (OR JUST AN ARRAY
                                                                                //for variable args
            System.out.println(q.getContents());
          //  st.ep.accept();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        */
        /*Command cmdTwo = null;
        Command cmd = new Command("concat","-nyan");

        Method[] m = String.class.getMethods();
        Map<String,Method> coolMap = new HashMap<String,Method>();
        for (Method M: m){
            coolMap.put(M.getName(),M);
        }

        Object o = " and again";

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("./cmd.ser")));
            oos.writeObject(cmd);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("./cmd.ser")));
            cmdTwo = (Command)ois.readObject();
            System.out.println(coolMap.get(cmdTwo.method).invoke("rawr", cmdTwo.args));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    */
    }
}
