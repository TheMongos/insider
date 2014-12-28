package db.mysql;

import javax.sql.DataSource;

public interface UserDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the User table.
	 */
	public Long create(String first_name, String last_name,
			String username, String email, String password);

	/**
	 * This is the method to be used to list down a record from the User table
	 * corresponding to a passed user_id.
	 */
	public User getUser(Integer user_id);
	
	/**
	 * This is the method to be used to delete a record from the User table
	 * corresponding to a passed user_id.
	 */
	public void delete(Integer user_id);
	
	/**
	 * This is the method to be used to update a record into the User table.
	 */
	public void updateFirstName(Integer user_id, String first_name);
	
	/**
	 * This is the method to be used to update a record into the User table.
	 */
	public void updateLastName(Integer user_id, String last_name);
	
	/**
	 * This is the method to be used to update a record into the User table.
	 */
	public void updateEmail(Integer user_id, String email);
	
	/**
	 * This is the method to be used to update a record into the User table.
	 */
	public void updatePassword(Integer user_id, String password);
}
