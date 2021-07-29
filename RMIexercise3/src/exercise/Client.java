package exercise;

import java.rmi.Remote;

public interface Client extends Remote {
	Client callback(Cell values) throws Exception;
}
