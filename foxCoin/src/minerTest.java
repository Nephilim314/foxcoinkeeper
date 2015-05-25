import java.io.IOException;

/**
 * Created by torrentglenn on 5/25/15.
 */
public class minerTest {
    public static void main(String[] args){
        MinerNode n = new MinerNode();
        try {
            System.out.println(n.BFGcommand(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
