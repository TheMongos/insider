package db.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		
		user.setUser_id(rs.getInt("user_id"));
		user.setFirst_name(rs.getString("first_name"));
		user.setLast_name(rs.getString("last_name"));
		user.setUsername(rs.getString("username"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setCreated_ts(rs.getTimestamp("created_ts"));
		user.setUpdated_ts(rs.getTimestamp("updated_ts"));
		
		return user;
	}
}
