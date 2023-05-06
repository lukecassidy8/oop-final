package com.example.oopfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The type Conn database.
 */
public class ConnDatabase {
    private static final String url = "jdbc:mysql://localhost:3306/users";
    private static final String database_username = "root";
    private static final String database_password = "Amanda140222!";
    private static final String loginQuery = "SELECT * FROM users.user_details WHERE user_username = ? and user_password = ?";
    private static final String registerQuery = "INSERT INTO users.user_details (user_username, user_password, user_email) VALUES (?, ?, ?)";
    private static final String checkDupeUsername = "SELECT * FROM users.user_details WHERE user_username = ?";

    /**
     * Validate login boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public boolean validateLogin(String username, String password){
        try(Connection connection = DriverManager.getConnection(url, database_username, database_password);
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }
        catch (SQLException e){
            printSQLException(e);
        }
        return false;
    }

    /**
     * Register user.
     *
     * @param username the username
     * @param password the password
     * @param email    the email
     */
    public static void registerUser(String username, String password, String email){
        try(Connection connection = DriverManager.getConnection(url, database_username, database_password);
            PreparedStatement preparedStatement = connection.prepareStatement(registerQuery)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            printSQLException(e);
        }
    }

    /**
     * Check dupe user boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public boolean checkDupeUser(String username){
        try(Connection connection = DriverManager.getConnection(url, database_username, database_password);
        PreparedStatement preparedStatement = connection.prepareStatement(checkDupeUsername)){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return false;
            }
        }catch (SQLException e){
            printSQLException(e);
        }
        return true;
    }

    /**
     * Print sql exception.
     *
     * @param ex the ex
     */
    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
