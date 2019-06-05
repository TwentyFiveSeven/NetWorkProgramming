

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientIF extends Remote{
	void recive(String word) throws RemoteException;
	String getname() throws RemoteException;

}
