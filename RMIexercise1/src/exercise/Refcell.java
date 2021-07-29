package exercise;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Refcell extends Remote {
	public void set(int v) throws RemoteException;
	public int get() throws RemoteException;
}
