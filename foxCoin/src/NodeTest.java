/**
 * Created by torrentglenn on 5/25/15.
 */
public class NodeTest {
    public static void main(String[] args){
        MinerNode n = new MinerNode();
        System.out.println(n.getId());
        for(;;){
            n.masterHook.execute();
        }
    }
}
