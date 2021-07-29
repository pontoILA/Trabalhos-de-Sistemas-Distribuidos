package exercise;

import java.rmi.Naming;
import java.util.Scanner;

public class Client {
	
	private static Scanner scan = null;

	public static void main(String[] args) {
		try {
			scan = new Scanner(System.in);
			Refcell refcellapi = (Refcell) Naming.lookup("rmi://localhost:1099/Refcell");
			for(int i = 0; i!=3;) {
				System.out.println("1- Ler\n2-escrever\n3-sair do servidor");
				int escolha = scan.nextInt();
				switch(escolha) {
					case 1:
						 System.out.println("Valor quardado: " + refcellapi.get());;
						break;
					case 2:
						refcellapi.set(scan.nextInt());
						break;
					case 3:
						i = 3;
						break;
					default:
						continue;
				}
				
			}
		} catch (Exception e) {
			System.out.println("Não funcionou... Algo deu errado");
			e.printStackTrace();
		}

	}

}
