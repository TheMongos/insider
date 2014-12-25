package db.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class ItemMapper implements RowMapper<Item> {
	public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
		Item item = new Item();
		
		item.setItem_id(rs.getInt("item_id"));
		item.setCategory_id(rs.getByte("category_id"));
		item.setTitle(rs.getString("title"));
		item.setYear(rs.getString("year"));
		item.setDescription(rs.getString("description"));
		item.setOther_data(rs.getString("other_data"));
		item.setCreated_ts(rs.getTimestamp("created_ts"));
		item.setUpdated_ts(rs.getTimestamp("updated_ts"));
		
		return item;
	}
}
