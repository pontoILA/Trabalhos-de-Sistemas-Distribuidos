package exercise;
import java.rmi.Remote;

public interface Server extends Remote{
	Cell getcell() throws Exception;
	Cell requestcell(Client client) throws Exception;
}
