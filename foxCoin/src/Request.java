import java.io.Serializable;
import java.util.Date;

/**
 * Created by torrentglenn on 5/24/15.
 */
public class Request implements Serializable {

    public static Request PostRequest(String ID, Command cmd, boolean returns){
        return new Request(ID,Req.POST,cmd,returns);
    }

    public static Request StatRequest(String ID){
        return new Request(ID,Req.STATUS,null,false);
    }

    public static Request GetRequest(String ID){
        return new Request(ID,Req.GET,null,false);
    }

    private String id;
    private Req type;
    private Object contents;
    private boolean returns;

    private Request(String ID, Req TYPE, Object cont, boolean b){
        id = ID;
        type = TYPE;
        contents = cont;
        returns = b;
    }

    public boolean notVoid(){
        return returns;
    }
    public Object getContents() {
        return contents;
    }
    public Req getType(){
        return type;
    }
    public String getId(){
        return id;
    }
}
