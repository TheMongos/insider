package db.mysql;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class CategoryJDBCTemplate implements CategoryDAO{

	String tableName = "Category";
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public void setDataSource(DataSource datasource) {
		this.dataSource = datasource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);

		String SQL = "CREATE TABLE IF NOT EXISTS "
				+ tableName
				+ " (category_id TINYINT NOT NULL"
				+ ", category_name CHAR(30) NOT NULL"
				+ ", primary key (category_id)"
				+ ") ENGINE = InnoDB CHARACTER SET utf8 COLLATE utf8_bin;";


		jdbcTemplateObject.execute(SQL);	
	}

	@Override
	public void create(byte category_id, String category_name) {
		String SQL = "insert into Category (category_id, category_name) values (?, ?)";

		jdbcTemplateObject.update(SQL, category_id, category_name);
		return;
	}

	@Override
	public Category getCategory(byte category_id) {
		String SQL = "select * from Category where category_id = ?";
		Category category = jdbcTemplateObject.queryForObject(SQL,
				new Object[] { category_id }, new CategoryMapper());
		return category;
	}

	@Override
	public void delete(byte category_id) {
		String SQL = "delete from Category where category_id = ?";
		jdbcTemplateObject.update(SQL, category_id);
		return;
	}

}
