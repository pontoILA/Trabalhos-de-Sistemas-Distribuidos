import java.rmi.Naming;


public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Server obj = (Server) Naming.lookup("rmi://localhost:1099/Server");
			Cell cell = obj.getcell();
			System.out.println("contents: " + cell.get());
			
		} catch (Exception e) {
			System.out.println("HelloClient exception: " + e.getMessage());
               e.printStackTrace();	
		}

	}

}
