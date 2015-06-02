import java.io.IOException;

/**
 * Created by torrentglenn on 6/2/15.
 */
public class INodeFire {
    public static void main(String[] args){
        try {
            INode IN = new INode(args[0],Constants.PROXY_PORT);

            String reqId;

            IN.contactMaster.fire(new Command("fireAll",new Command(args[1])));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
