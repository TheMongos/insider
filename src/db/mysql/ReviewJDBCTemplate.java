package db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

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
				+ ", review_text VARCHAR(500) NOT NULL "
				+ ", created_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
				//+ ", updated_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
				+ ", primary key (review_id)"
				+ ", FOREIGN KEY (user_id)"
				+ "		REFERENCES User(user_id)"
				+ "		ON UPDATE CASCADE ON DELETE SET NULL"
				+ ", FOREIGN KEY (item_id)"
				+ "		REFERENCES Item(item_id)"
				+ "		ON UPDATE CASCADE ON DELETE CASCADE"
				+ ") ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin;";



		jdbcTemplateObject.execute(SQL);
	}

	@Override
	public Long create(int user_id, int item_id, String review_text) {
		String SQL = "insert into Review (user_id, item_id, review_text)"
				+ " values (?, ?, ?)";

		//jdbcTemplateObject.update(SQL, user_id, item_id, review_text);

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplateObject.update(new PreparedStatementCreator() {           

			@Override
			public PreparedStatement createPreparedStatement(Connection connection)
					throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] {"item_id"});
				ps.setInt(1, user_id);
				ps.setInt(2, item_id);
				ps.setString(3, review_text);
				return ps;
			}
		}, holder);

		return (Long)holder.getKey();

	}

	@Override
	public Review getReview(Integer review_id) {
		String SQL = "select * from Review where review_id = ?";
		Review review = null;
		try {
			review =jdbcTemplateObject.queryForObject(SQL,
					new Object[] { review_id }, new ReviewMapper());
		} catch (Exception e) {

		}
		return review;
	}

	@Override
	public boolean delete(Integer review_id, Integer user_id) {
		String SQL = "delete from Review where review_id = ? and user_id = ?";
		int res = jdbcTemplateObject.update(SQL, review_id, user_id);
		return (res == 0) ? false : true;
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

	@Override
	public void update(int review_id, String review_text) {
		String SQL = "update Review set review_text = ? where review_id = ?";
		jdbcTemplateObject.update(SQL, review_text, review_id);
	}

}
