package db.mysql;

import javax.sql.DataSource;

public interface CategoryDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the Category table.
	 */
	public void create(byte category_id, String category_name);

	/**
	 * This is the method to be used to list down a record from the Category
	 * table corresponding to a passed category_id.
	 */
	public Category getCategory(byte category_id);

	/**
	 * This is the method to be used to delete a record from the Category table
	 * corresponding to a passed category_id.
	 */
	public void delete(byte category_id);

}
