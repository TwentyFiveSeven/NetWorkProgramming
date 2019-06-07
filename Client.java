

import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements ClientIF, Runnable{
	Scanner scan = new Scanner(System.in);

	private ServerIF server;
	private String name = null;
	protected Client(String name, ServerIF server) throws RemoteException {
		this.name = name;
		this.server = server;
		server.registerClient(this, this.name);
	}

	@Override
	public void recive(String word) throws RemoteException {
		System.out.println(word);		
	}
	public String getname() throws RemoteException {
		return this.name;
	}

	@Override
	public void run() {
		String word=null;
//		word = scan.next();
		while(true) {
			word = scan.nextLine();
			if(word.equals("quit")) {
				try {
					
					server.removeClient(this);
					server.sendAll("quit", name);
					
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}

			else {
			try {
	//			word = scan.next();
				server.sendAll(word, name);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			}
		}
	}

}
