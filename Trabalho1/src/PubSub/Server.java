package PubSub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
	void subscribe(Client c, Integer key) throws RemoteException;
	void publish(Integer key, Cell o, String publisher) throws RemoteException;
}
