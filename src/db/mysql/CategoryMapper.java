package db.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CategoryMapper  implements RowMapper<Category> {
	public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
		Category category = new Category();
		
		category.setCategory_name(rs.getString("category_name"));
		category.setCategory_id(rs.getByte("category_id"));
		
		return category;
	}
}
