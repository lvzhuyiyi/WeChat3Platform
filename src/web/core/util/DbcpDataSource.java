package web.core.util;

import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import java.sql.DriverManager;
import org.apache.commons.dbcp.BasicDataSource;
import java.sql.SQLException;
public class DbcpDataSource extends BasicDataSource {

	
	 @Override   
    public   synchronized   void  close()  throws  SQLException {  
        DriverManager.deregisterDriver(DriverManager.getDriver(url));  
        super .close();  
    }  
	

}
