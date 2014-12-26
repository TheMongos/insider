package db.mysql;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class UserJDBCTemplate implements UserDAO{

	private String tableName = "User";
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	// @Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);

		String SQL = "CREATE TABLE IF NOT EXISTS "
				+ tableName
				+ " (user_id INT NOT NULL AUTO_INCREMENT"
				+ ", first_name CHAR(35) NOT NULL "
				+ ", last_name CHAR(35) NOT NULL "
				+ ", username CHAR(30) NOT NULL"
				+ ", email CHAR(255) NOT NULL"
				+ ", password CHAR(64) NOT NULL"
				+ ", created_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
				+ ", updated_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
				+ ", primary key (user_id)"
				+ ") ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin;";

		System.out.println(SQL);

		jdbcTemplateObject.execute(SQL);
	}

	@Override
	public void create(String first_name, String last_name, String username,
			String email, String password) {
		String SQL = "insert into User (first_name, last_name, username, email"
				+ ",password) values (?, ?, ?, ?, ?)";

		jdbcTemplateObject.update(SQL, first_name, last_name, username, email,
				password);
		return;

	}

	@Override
	public User getUser(Integer user_id) {
		String SQL = "select * from User where user_id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { user_id }, new UserMapper());
		return user;
	}

	@Override
	public void delete(Integer user_id) {
		String SQL = "delete from User where user_id = ?";
		jdbcTemplateObject.update(SQL, user_id);
		return;

	}

	@Override
	public void updateFirstName(Integer user_id, String first_name) {
		String SQL = "update User set first_name = ? where user_id = ?";
		jdbcTemplateObject.update(SQL, first_name, user_id);
		return;
	}

	@Override
	public void updateLastName(Integer user_id, String last_name) {
		String SQL = "update User set last_name = ? where user_id = ?";
		jdbcTemplateObject.update(SQL, last_name, user_id);
		return;
	}

	@Override
	public void updateEmail(Integer user_id, String email) {
		String SQL = "update User set email = ? where user_id = ?";
		jdbcTemplateObject.update(SQL, email, user_id);
		return;
	}

	@Override
	public void updatePassword(Integer user_id, String password) {
		String SQL = "update User set password = ? where user_id = ?";
		jdbcTemplateObject.update(SQL, password, user_id);
		return;
	}

}