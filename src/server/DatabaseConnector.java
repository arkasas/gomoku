package server;

import model.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseConnector {

    private String url = "localhost";
    private String username = "root";
    private String password = "";
    private Integer port = 3306;
    private String databaseName = "gomoku";

    private Connection connection;
    private Statement statement;

    public DatabaseConnector() {
        loadDriver();
        connection = connectToBase();
        statement = createStatement(connection);
        createDatabase();
        selectDatabase();
        createTables();
    }

    private void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection connectToBase() {
        Connection conn = null;
        Properties properties = new Properties();
        properties.put("user", username);
        properties.put("password", password);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + url + ":" + port + "/", properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    private void createDatabase() {
        execute( "create Database " + databaseName + ";");
    }

    private void selectDatabase() {
        execute("USE " + databaseName + ";");
    }

    private void createTables() {
        createBoardTable();
//        createBookTable();
//        createSignTable();
//        createWordsTable();
    }

    private void createBoardTable() {
        execute("CREATE TABLE `gomoku`.`board` ( `id` INT NOT NULL AUTO_INCREMENT , `user_one` TEXT NOT NULL , `user_two` TEXT NULL , `board` TEXT NULL , `state` VARCHAR(255) NOT NULL DEFAULT 'WAITING' , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
    }

//    private void createBookTable() {
//        execute(statement, "CREATE TABLE book ( `id` INT NOT NULL, " +
//                "`author` VARCHAR(1024) NOT NULL, `title` VARCHAR(1024) NOT NULL, " +
//                "`publish_year` INT NOT NULL, PRIMARY KEY (`id`));");
//    }
//
//    private void createSignTable() {
//        execute(statement, "CREATE TABLE signs ( `id` INT NOT NULL, " +
//                "`key` VARCHAR(1) NOT NULL, `value` INT NOT NULL, `book_id` INT NOT NULL, PRIMARY KEY (`id`));");
//    }
//
//    private void createWordsTable() {
//        execute(statement, "CREATE TABLE words ( `id` INT NOT NULL, " +
//                "`key` VARCHAR(1) NOT NULL, `value` INT NOT NULL, `book_id` INT NOT NULL, PRIMARY KEY (`id`));");
//    }

    public ArrayList<Board> getFreeBoard() {
        ResultSet set = executeWithResult("SELECT * FROM board");
        ArrayList<Board> boards = new ArrayList<>();
        try {
            ResultSetMetaData rsmd = set.getMetaData();
            while (set.next()) {
               Board b = new Board();
               b.setId(set.getInt("id"));
               b.setUserOne(set.getString("user_one"));
               b.setUserTwo(set.getString("user_two"));
               b.setBoard(set.getString("board"));
               b.setState(set.getString("state"));
               boards.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boards;
    }

    public boolean createBoard(String userId) {
        int result = execute("INSERT INTO `board` (`id`, `user_one`, `user_two`, `board`, `state`) VALUES (NULL, '" + userId + "', NULL, NULL, 'WAITING');");
        if (result == -1) {
            return false;
        }
        return true;
    }

    public boolean joinToBoard(String userId, String boardId) {
        int result = execute("UPDATE `board` SET `user_two` = '"+  userId + "', `state` = 'STARTED' WHERE `board`.`id` = "+ boardId + ";");
        if (result == -1) {
            return false;
        }
        return true;
    }

    public String getBoardUserOne(String boardId) {
        ResultSet set = executeWithResult("SELECT user_one from board WHERE id = " + boardId);
        try {
            ResultSetMetaData rsmd = set.getMetaData();
            while (set.next()) {
                return set.getString("user_one");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getBoardByUser(String userid) {
        ResultSet set = executeWithResult("SELECT board FROM `board` WHERE user_one = '" + userid + "' OR user_two = '" + userid + "'");
        try {
            ResultSetMetaData rsmd = set.getMetaData();
            while (set.next()) {
                return set.getString("board");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int execute(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public ResultSet executeWithResult(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void closeConnection() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
