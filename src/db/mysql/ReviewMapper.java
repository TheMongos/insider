package db.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class ReviewMapper implements RowMapper<Review> {
	public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
		Review review = new Review();
		
		review.setReview_id(rs.getInt("review_id"));
		review.setUser_id(rs.getInt("user_id"));
		review.setItem_id(rs.getInt("item_id"));
		//review.setRank(rs.getByte("rank"));
		review.setReview_text(rs.getString("review_text"));
		review.setCreated_ts(rs.getTimestamp("created_ts"));
		review.setUpdated_ts(rs.getTimestamp("updated_ts"));

		return review;
	   }
}
