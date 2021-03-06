package db.mysql;

import java.util.List;

import javax.sql.DataSource;

public interface ReviewDAO {
	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setDataSource(DataSource ds);

	/**
	 * This is the method to be used to create a record in the Review table.
	 */
	public Long create(int user_id, int item_id, String review_text);

	/**
	 * This is the method to be used to list down a record from the Review table
	 * corresponding to a passed review_id.
	 */
	public Review getReview(Integer review_id);

	/**
	 * This is the method to be used to delete a record from the Review table
	 * corresponding to a passed review_id.
	 */
	public boolean delete(Integer review_id, Integer user_id);

	/**
	 * This is the method to be used to list down all the records from the
	 * Review table
	 * corresponding to a passed item_id.
	 */
	public List<Review> getItemReviews(Integer item_id);
	
	/**
	 * This is the method to be used to list down all the records from the
	 * Review table
	 * corresponding to a passed user_id.
	 */
	public List<Review> getUserReviews(Integer user_id);

	/**
	 * This is the method to be used to update the review text
	 * corresponding to a passed review_id.
	 */
	public void update(int review_id, String review_text);
}
