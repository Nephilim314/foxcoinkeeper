import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by torrentglenn on 5/21/15.
 */





public class Rmain {
    public static void main(String[] args){

        thing t = new thing("localhost", Constants.PROXY_PORT);
//        subThing st = new subThing("RAWR");

        try {
          //  st.ep.listen(Constants.PROXY_PORT);
          //  Response r = (Response) t.p.invoke("roar");
          //  System.out.println(r.getContents());
            Response q = (Response) t.p.invoke("sum", new int[]{1, 3, 5, 1,4, 5,22}); //AHA NEW ARRAY LITERAL (OR JUST AN ARRAY
                                                                                //for variable args
            System.out.println(q.getContents());
          //  st.ep.accept();
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
