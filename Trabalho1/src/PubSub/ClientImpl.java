package PubSub;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Scanner;

public class ClientImpl extends UnicastRemoteObject implements Client, Serializable {
	private String serverName;
	
	public ClientImpl() throws RemoteException {
		super();
	}

	

	public String getName() throws RemoteException {
		return this.serverName;
	}


	public void setName(String name) throws RemoteException {
		this.serverName = name;

	}
	

	public void showMessage(Cell c, String publisher) throws RemoteException {
		System.out.println("Message from "+ publisher +": " + c.getMessage());
		
	}

	public static void main(String[] args) {
		int B = 0; // key for publishing
        int A = 0; // key for subscribing
        String host, name, message;
        
        System.out.println("Argumentos: " + Arrays.toString(args));

        if (args.length == 4){
            host = args[0];
            name = args[1];
            A = Integer.parseInt(args[2]);
            B = Integer.parseInt(args[3]);
        }
        else if (args.length == 3){
            host = ""; 
            name = args[0];
            A = Integer.parseInt(args[1]);
            B = Integer.parseInt(args[2]);
        }
        else{
            host = "";
            name = "";
            System.err.println ("Usage: java Client [[host] port] sub_key pub_key");
            System.exit(1);
        }
        
        

		try {
			Client client = new ClientImpl();
			client.setName(name);
			
			Client stub_client = (Client) new ClientImpl();
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub_client);
			
			registry = LocateRegistry.getRegistry(host);
            Server stub = (Server) registry.lookup("PubSubServer");
			
            Scanner scan = new Scanner(System.in);
			
			System.out.println("Subscribind to: " + A + "\nPublishing: " + B + "\n");

            if (A != 0){          
                Integer sub_key;
                sub_key = A;
                stub.subscribe(client, sub_key);
            }
           
            if (B != 0){
                Integer pub_key;
                pub_key = B;
                Cell o = (Cell) new CellImpl(); // Create a fresh object
                System.out.print("O que deseja escrever: ");
                message = scan.nextLine();
                o.setMessage(message);
                stub.publish(pub_key, o, name);  // Publish the object
            }
            
            scan.close();
		} catch (Exception e) {
			System.out.println("PubSubClient exception: " + e.getMessage());
            e.printStackTrace();	
		}

	}

}
