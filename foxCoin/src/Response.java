import java.io.Serializable;

/**
 * Created by torrentglenn on 5/21/15.
 */
public class Response implements Serializable{
    private Object contents;

    public Object getContents(){
        return contents;
    }

    public Response(Object c){
        contents = c;
    }
}
