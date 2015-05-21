import java.io.Serializable;

/**
 * Created by torrentglenn on 5/21/15.
 */
public class Command implements Serializable {
    public Object[] args;
    public String method;

    public Command(String s,Object ... args){
        this.method = s;
        this.args = new Object[args.length];
        for (int i = 0; i < args.length; i++){
            this.args[i] = args[i];
        }
    }
}