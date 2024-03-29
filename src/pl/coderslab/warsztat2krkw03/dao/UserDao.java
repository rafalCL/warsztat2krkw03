package pl.coderslab.warsztat2krkw03.dao;

import pl.coderslab.warsztat2krkw03.db.Db;
import pl.coderslab.warsztat2krkw03.model.User;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    public static User create(User user){
        try(Connection connection
            = DriverManager.getConnection(
                    Db.URL, Db.USER, Db.PASSWORD)) {
            final PreparedStatement ps = connection.prepareStatement(
                    CREATE_USER_QUERY,
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();

            final ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()){
                user.setId(generatedKeys.getInt(1));
            }

            return user;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    } // create()
}
