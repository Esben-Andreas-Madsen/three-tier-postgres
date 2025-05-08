package database.postgres;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;

public interface IDatabaseConnection extends Remote {
     Connection getConnection();
}
