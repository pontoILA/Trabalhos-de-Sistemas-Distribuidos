package PubSub;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class ServerImpl extends UnicastRemoteObject implements Server {
	
	private Map<Integer, Cell> obj_map = new HashMap<Integer, Cell>();
	private Map<Integer, HashSet<Client>> sub_map = new HashMap<Integer, HashSet<Client>>();
	
	public ServerImpl() throws RemoteException {
		super();
	}

	


	public void subscribe(Client c, Integer key) throws RemoteException {
		
		HashSet<Client> set = sub_map.get(key);
        if(set == null) set = new HashSet<Client>();
        set.add(c);
        sub_map.put(key, set);
        Cell o = obj_map.get(key);
        
        if(o!=null){
            try{
            	Registry registry = LocateRegistry.getRegistry();
                Client obj = (Client) registry.lookup(c.getName());
                obj.showMessage(o, "(Publicacoes anteriores)");
            } catch (Exception e){
            	System.out.println("PubSubServer err: " + e.getMessage());
                e.printStackTrace();
            }
        }

	}


	public void publish(Integer key, Cell o, String publisher) throws RemoteException {
		
		obj_map.put(key, o);
		
        HashSet<Client> subs = sub_map.get(key);

        if(subs != null) {
        	for(Client c : subs){
                try{
                    Registry registry = LocateRegistry.getRegistry();
                    Client obj = (Client) registry.lookup(c.getName());
                    obj.showMessage(o, publisher);
                } catch (Exception e){
                	System.out.println("PubSubServer err: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

	}


	public static void main(String[] args) {
		try {
			Server obj = new ServerImpl();
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("PubSubServer", obj);
			
			System.out.println("PubSubServer ready...");
			
		} catch (Exception e) {
			System.out.println("PubSubServer err: " + e.getMessage());
            e.printStackTrace();	
		}

	}

}
