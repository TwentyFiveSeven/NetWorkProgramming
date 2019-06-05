


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIF extends Remote{
	void registerClient(ClientIF client, String name) throws RemoteException;
	void removeClient(ClientIF client) throws RemoteException;
	String checkword() throws RemoteException;
	void sendAll(String word, String turn) throws RemoteException;
}
