package db.mysql;

import java.sql.Timestamp;

public class Item {
	private int item_id;
	private byte category_id;
	private String title;
	private String year;
	private String description;
	private String other_data;
	private Timestamp created_ts;
	private Timestamp updated_ts;
	
/*	public Item(){} 
	public Item(int item_id, byte category, String title, String year,
			String description, String other_data, Timestamp created_ts,
			Timestamp updated_ts) {
		super();
		this.item_id = item_id;
		this.category = category;
		this.title = title;
		this.year = year;
		this.description = description;
		this.other_data = other_data;
		this.created_ts = created_ts;
		this.updated_ts = updated_ts;
	}*/
	
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public byte getCategory_id() {
		return category_id;
	}
	public void setCategory_id(byte category_id) {
		this.category_id = category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOther_data() {
		return other_data;
	}
	public void setOther_data(String other_data) {
		this.other_data = other_data;
	}
	public Timestamp getCreated_ts() {
		return created_ts;
	}
	public void setCreated_ts(Timestamp created_ts) {
		this.created_ts = created_ts;
	}
	public Timestamp getUpdated_ts() {
		return updated_ts;
	}
	public void setUpdated_ts(Timestamp updated_ts) {
		this.updated_ts = updated_ts;
	}
	
	
}
