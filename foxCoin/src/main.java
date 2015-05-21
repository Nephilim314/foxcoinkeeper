import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by torrentglenn on 5/21/15.
 */





public class main {
    public static void main(String[] args){

        //thing t = new thing("localhost", Constants.PROXY_PORT);
        subThing st = new subThing("RAWR");

        try {
            st.ep.listen(Constants.PROXY_PORT);
          //  System.out.println((String) t.p.invoke("roar"));
        for(;;) {
            st.ep.accept();
        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
