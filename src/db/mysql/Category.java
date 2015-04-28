package db.mysql;

public class Category {
	private byte category_id;
	private String category_name;
	
/*	public Category(){}
	public Category(byte category_num, String category_name) {
		super();
		this.category_num = category_num;
		this.category_name = category_name;
	}*/
	
	
	public byte getCategory_id() {
		return category_id;
	}
	public void setCategory_id(byte category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	
	
}
