package db.mysql;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class ReviewJDBCTemplate implements ReviewDAO {

	private String tableName = "Review";
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	// @Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);

		String SQL = "CREATE TABLE IF NOT EXISTS "
				+ tableName
				+ " (review_id INT NOT NULL AUTO_INCREMENT"
				+ ", user_id INT "
				+ ", item_id INT NOT NULL "
				+ ", rank TINYINT NOT NULL"
				+ ", review_text VARCHAR(500) NOT NULL "
				+ ", created_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
				+ ", updated_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
				+ ", primary key (review_id)"
				+ ", FOREIGN KEY (user_id)"
				+ "		REFERENCES User(user_id)"
				+ "		ON UPDATE CASCADE ON DELETE SET NULL"
				+ ", FOREIGN KEY (item_id)"
				+ "		REFERENCES Item(item_id)"
				+ "		ON UPDATE CASCADE ON DELETE CASCADE"
				+ ") ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin;";

		System.out.println(SQL);

		jdbcTemplateObject.execute(SQL);
	}

	@Override
	public void create(int user_id, int item_id, byte rank, String review_text) {
		String SQL = "insert into Review (user_id, item_id, rank, review_text)"
				+ " values (?, ?, ?, ?)";

		jdbcTemplateObject.update(SQL, user_id, item_id, rank, review_text);
		return;
		
	}

	@Override
	public Review getReview(Integer review_id) {
		String SQL = "select * from Review where review_id = ?";
		Review review = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { review_id }, new ReviewMapper());
		return review;
	}

	@Override
	public void delete(Integer review_id) {
		String SQL = "delete from Review where review_id = ?";
		jdbcTemplateObject.update(SQL, review_id);
		return;
		
	}

	@Override
	public List<Review> getItemReviews(Integer item_id) {
		String SQL = "select * from Review where item_id = ?";
		List<Review> reviews = jdbcTemplateObject.query(SQL,new Object[] { item_id } , new ReviewMapper());
		return reviews;
	}

	@Override
	public List<Review> getUserReviews(Integer user_id) {
		String SQL = "select * from Review where user_id = ?";
		List<Review> reviews = jdbcTemplateObject.query(SQL,new Object[] { user_id } , new ReviewMapper());
		return reviews;
	}

}
