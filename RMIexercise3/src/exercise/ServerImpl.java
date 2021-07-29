package exercise;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

public class ServerImpl extends UnicastRemoteObject implements Server {

	public ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	public Cell getcell() {
		Cell obj = (Cell) new CellImpl();
		Calendar rightNow = Calendar.getInstance();
		obj.set(rightNow.get(Calendar.SECOND));
		return obj;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Server obj = new ServerImpl();
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Server", obj);
			System.out.println("ServerImpl bound in registry");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ServerImpl err: " + e.getMessage());
            e.printStackTrace();
		}

	}


	@Override
	public Cell requestcell(Client client) throws Exception {
		client.callback((Cell) new CellImpl());
		return null;
	}

}
