package PubSub;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Client extends Remote {
	public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;
	public void showMessage(Cell c, String publisher) throws RemoteException;
}
