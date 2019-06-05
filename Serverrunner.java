


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Serverrunner {
	
	public static void main(String[] args) throws RemoteException, MalformedURLException{
		Naming.rebind("rmi://"+args[0]+":1099/"+args[1], new Server());

	}

}
