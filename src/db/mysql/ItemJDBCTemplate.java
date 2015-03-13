package db.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class ItemJDBCTemplate implements ItemDAO {

	private String tableName = "Item";
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	// @Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);

		String SQL = "CREATE TABLE IF NOT EXISTS "
				+ tableName
				+ " (item_id INT NOT NULL AUTO_INCREMENT"
				+ ", category_id TINYINT "
				+ ", title VARCHAR(500) NOT NULL "
				+ ", year CHAR(15) NOT NULL"
				+ ", description VARCHAR(1200) NOT NULL"
				+ ", other_data VARCHAR(1000) NOT NULL"
				+ ", created_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
				+ ", updated_ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
				+ ", primary key (item_id)"
				+ ", FOREIGN KEY (category_id)"
				+ "		REFERENCES Category(category_id)"
				+ "		ON UPDATE CASCADE ON DELETE SET NULL"
				+ ") ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin;";

		System.out.println(SQL);

		jdbcTemplateObject.execute(SQL);
	}

	// @Override
	public Long create(byte category_id, String title, String year,
			String description, String other_data) {
		String SQL = "insert into Item (category_id, title, year, description"
				+ ",other_data) values (?, ?, ?, ?, ?)";

		//jdbcTemplateObject.update(SQL, category_id, title, year, description, other_data);
		
		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplateObject.update(new PreparedStatementCreator() {           

		                @Override
		                public PreparedStatement createPreparedStatement(Connection connection)
		                        throws SQLException {
		                    PreparedStatement ps = connection.prepareStatement(SQL, new String[] {"item_id"});
		                    ps.setByte(1, category_id);
		                    ps.setString(2, title);
		                    ps.setString(3, year);
		                    ps.setString(4, description);
		                    ps.setString(5, other_data);
		                    return ps;
		                }
		            }, holder);

		return (Long)holder.getKey();
		
	}

	// @Override
	public Item getItem(Integer item_id) {
		String SQL = "select * from Item where item_id = ?";
		Item item = null;
		try{
			item = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { item_id }, new ItemMapper());
		} catch (Exception e){
			
		}
		return item;
	}

	// @Override
	public void delete(Integer item_id) {
		String SQL = "delete from Item where item_id = ?";
		jdbcTemplateObject.update(SQL, item_id);
		return;
	}

	// @Override
	public void updateDescription(Integer item_id, String description) {
		String SQL = "update Item set description = ? where item_id = ?";
		jdbcTemplateObject.update(SQL, description, item_id);
		return;
	}

	@Override
	public List<Item> getItems() {
		String SQL = "select * from Item";
		List<Item> items = jdbcTemplateObject.query(SQL, new ItemMapper());
		return items;
	}

	@Override
	public List<String> getTitles(String query) {
		List<String> res = new ArrayList<String>();
		String SQL = "select i.category_id, i.item_id, i.title, c.category_name from Item i join Category c on i.category_id = c.category_id where lower(i.title) like lower(?)";
		List<Map<String, Object>> rows = jdbcTemplateObject.queryForList(SQL, query+ "%");
		for(@SuppressWarnings("rawtypes") Map row : rows){
			res.add("{ \"item_id\" : " + (Integer)row.get("item_id") + ", \"title\" : \"" + (String)row.get("title") + "\", \"category_id\": " + (Integer)row.get("category_id") + ", \"category_name\" : \"" + (String)row.get("category_name") +"\"}");
		}
		//List<User> reviews = jdbcTemplateObject.query(SQL,new Object[] { query+"%" } , new UserMapper());
		return res;
	}
}
