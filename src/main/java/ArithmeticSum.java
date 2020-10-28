import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class ArithmeticSum {
    private Logger logger = LoggerFactory.getLogger(ArithmeticSum.class);
    private Connection connection = null;

    public ArithmeticSum(DataBaseInfo dataBaseInfo) {
        try {
            connection = DriverManager.getConnection(dataBaseInfo.getUrl(), dataBaseInfo.getName(), dataBaseInfo.getPassword());

            logger.info("Created connection");
            logger.debug("URL for Connection: " + dataBaseInfo.getUrl());
            logger.debug("Name for Connection: " + dataBaseInfo.getName());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
    }

    public void start(DataBaseInfo dataBaseInfo) {
        try {
            Class.forName(dataBaseInfo.getDriver());
            logger.info("Used driver for JDBC: " + dataBaseInfo.getDriver());

            if(getCountFieldsInDB() >= 1){
                if(deleteFields()) {
                    logger.info("Deleted all fields in table");
                }
                else {
                    logger.warn("Couldn't delete all fields in the table");
                }
            }

            insertFieldsInDB(dataBaseInfo);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    public int getCountFieldsInDB() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            logger.debug("Created a Statement for Connection to FIND out the number of fields in the table");

            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as count FROM TEST");
            resultSet.next();

            logger.debug("Query completed. The number of fields in the table: " + resultSet.getInt(1));

            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }

        return -1;
    }

    public boolean deleteFields() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            logger.debug("Created a Statement for Connection to DELETE the fields in the table");

            statement.executeUpdate("DELETE FROM TEST WHERE id > 0");
            logger.debug("Query completed.");

            if(getCountFieldsInDB() == 0) return true;
            else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }

        return false;
    }

    public int insertFieldsInDB(DataBaseInfo dataBaseInfo) {
        Statement statement = null;
        try {
            int N = dataBaseInfo.getN();

            if(N >= 1) {
                statement = connection.createStatement();

                logger.debug(String.format("Created a Statement for Connection to insert the %s fields in the table", N));

                for (int i = 0; i < N; i++) {
                    String insertSQL = String.format("INSERT INTO TEST (\"field\") VALUES ('%d')", i);

                    statement.addBatch(insertSQL);
                }

                int number = statement.executeBatch().length;
                logger.debug(String.format("Inserted %d fields", number));

                return statement.executeBatch().length;
            }
            else return 0;
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }

        return 0;
    }
}
