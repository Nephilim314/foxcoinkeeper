/**
 * Created by torrentglenn on 5/21/15.
 */
public class thing{
    public Proxy subThingProxy;

    public thing(String ep, int port){
        subThingProxy = new Proxy(ep,port);

    }
}
