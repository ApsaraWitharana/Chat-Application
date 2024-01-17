package lk.ijse.Model;


import lk.ijse.dbConnection.DbConnection;
import lk.ijse.dto.LoginDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    public boolean saveUser(LoginDTO loginDto ) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement("INSERT INTO users VALUES (?,?,?) ");
        pstm.setString(2, loginDto.getUser_name());
        pstm.setString(3, loginDto.getPassword());

        return pstm.executeUpdate()>0;
    }

    public static boolean isCurrect(String uerName, String password) throws SQLException {

        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM users WHERE user_name=? && password=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, uerName);
        pstm.setObject(2, password);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

}
