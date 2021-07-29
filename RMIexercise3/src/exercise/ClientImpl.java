package exercise;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ClientImpl extends UnicastRemoteObject implements Client{

	protected ClientImpl() throws RemoteException {
		super();
	}

	public Client callback(Cell values) {
		System.out.println("Content: " + values.get());
		return null;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Client obj_client = (Client) new ClientImpl();
			Server obj = (Server) Naming.lookup("rmi://localhost:1099/Server");
			
			obj.requestcell(obj_client);
			
		} catch (Exception e) {
			System.out.println("HelloClient exception: " + e.getMessage());
               e.printStackTrace();	
		}

	}


}
