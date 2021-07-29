package exercise;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Refcell {
	
	private int  contents = 0;

	public Server() throws RemoteException {
		super();
	}


	@Override
	public void set(int v) throws RemoteException {
		contents = v;

	}

	@Override
	public int get() throws RemoteException {
		return contents;
	}

	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Refcell", new Server());
			System.out.println("refcell Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
		}

	}

}
