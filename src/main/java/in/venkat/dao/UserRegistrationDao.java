package in.venkat.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import in.venkat.exceptions.DbException;
import in.venkat.model.UserRegistration;
import in.venkat.util.ConnectionUtil;
import in.venkat.util.Logger;

public class UserRegistrationDao {
	private UserRegistrationDao() {
		/**
		 * adding a private constructor
		 */

	}

	/**
	 * This method is used to store the registered user details
	 * 
	 * @param register
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DbException
	 */
	public static void register(UserRegistration register) throws SQLException, DbException {
		Connection connection = null;
		PreparedStatement pst = null;
		try {

			connection = ConnectionUtil.getConnection();

			String sql = "insert into users (user_id,name,phone_number,password,joined_on)  values(?,?,?,?,now());";

			pst = connection.prepareStatement(sql);
			pst.setString(1, register.getUserId());
			pst.setString(2, register.getUserName());
			pst.setLong(3, register.getPhoneNumber());
			pst.setString(4, register.getPassword());

			pst.executeUpdate();

		} catch (SQLException e) {
			Logger.exception(e);
			throw new DbException("unable to add user");

		} finally {
			ConnectionUtil.close(pst, connection);

		}

	}
}
