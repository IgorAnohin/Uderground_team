import java.sql.*;
import java.util.Optional;

public class DBConnect {
        private static Connection connection;

        public enum TypeQuery {
                SELECT,
                UPDATE,
                CREATE,
        }

        public static void init() {
                try {
                        Class.forName("com.mysql.jdbc.Driver");
                        System.out.println("Driver loaded!");
                } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("Cannot find the driver in the classpath!", e);
                }

                // String url = "jdbc:mysql://178.162.38.113:3306/test42";
                String url = "jdbc:mysql://10.20.3.110:3306/test42";
                
                String username = "stranger";
                String password = "12345";

                try {
                        connection = DriverManager.getConnection(url, username, password);
                } catch (SQLException e) {
                        throw new RuntimeException("Connection is failed.");
                }
        }

        public static ResultSet sendQuery(TypeQuery type, String query) {
                if (connection == null)
                        throw  new RuntimeException("Base doesn't initialized.");

                try {
                        Statement stmt = connection.createStatement();
                        switch (type) {
                                case SELECT: {
                                        return stmt.executeQuery(query);
                                }

                                case UPDATE: {
                                        stmt.executeUpdate(query); // insert, update, delete
                                        break;
                                }

                                case CREATE: {
                                        stmt.execute(query); // сreate table
                                        break;
                                }
                        }
                } catch (SQLException e) {
                        throw new RuntimeException("Something wrong with your query");
                }
                return null;
        }

        public static void showTables() {
                try {
                        DatabaseMetaData meta = connection.getMetaData();
                        ResultSet tables = meta.getTables(null, null, "%", null);

                        while (tables.next())
                                System.out.println(tables.getString(3));
                } catch (SQLException e) {
                        throw new RuntimeException("Failed with get metadata.");
                }

        }

        public static void descTable(String name) {
                try {
                          Optional<ResultSet> optSet = Optional.ofNullable(sendQuery(TypeQuery.SELECT, "select * from "+name));
                          if (!optSet.isPresent())
                                return;

                        ResultSet columns = optSet.get();
                        ResultSetMetaData columnsData = columns.getMetaData();
                        int columnsCount = columnsData.getColumnCount();

                        System.out.printf("In table %s:", name);
                        for (int i = 1; i <= columnsCount; i++) {
                                System.out.printf("%s %s\n", columnsData.getColumnName(i), columnsData.getColumnTypeName(i));
                        }
                } catch (SQLException e) {
                        throw new RuntimeException("Failed with get metadata.");
                }

        }

}
