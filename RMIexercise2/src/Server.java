import java.rmi.Remote;

public interface Server extends Remote{
	Cell getcell() throws Exception;
}
