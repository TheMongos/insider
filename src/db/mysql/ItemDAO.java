package db.mysql;

import java.util.List;

import javax.sql.DataSource;

public interface ItemDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the Item table.
	 */
	public Long create(byte category, String title, String year,
			String description, String other_data);

	/**
	 * This is the method to be used to list down a record from the Item table
	 * corresponding to a passed item_id.
	 */
	public Item getItem(Integer item_id);

	/**
	 * This is the method to be used to delete a record from the Item table
	 * corresponding to a passed item_id.
	 */
	public void delete(Integer item_id);

	/**
	 * This is the method to be used to update a record into the Item table.
	 */
	public void updateDescription(Integer item_id, String description);
	
	/**
	 * This is the method to be used to list down all the records from the
	 * Item table
	 */
	public List<Item> getItems();
}
