package swt.group.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import swt.group.ApplicationProperties;
import swt.group.betrieb.BetriebData;
import swt.group.uni.StudiumData;

/**
 * Provides a connection pool. Use {@link getConnection()} to get a connection
 * instead of establishing your own connection when calling the database.
 *
 * @author hvo
 */
public class JDBCConnection {

    final private static Logger LOG = LoggerFactory.getLogger(JDBCConnection.class);

    private final static BasicDataSource dataSource = new BasicDataSource();

    //set up a connection pool
    static {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(ApplicationProperties.getDbUrl());
        dataSource.setUsername(ApplicationProperties.getDbUser());
        dataSource.setPassword(ApplicationProperties.getDbPassword());
    }

    private JDBCConnection() {

    }

    /**
     * Used to get a connection from the connection pool.
     *
     * @return connection from the connection pool
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    //main method is not needed for production, it is just there for testing purposes
    public static void main(String[] args) {
        LOG.info(PersonRepository.retrievePasswordForUser("pp@mail.de"));
    }
}
