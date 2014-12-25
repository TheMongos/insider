package db.mysql;

import java.sql.Timestamp;

public class User {
	private int user_id;
	private String first_name;
	private String last_name;
	private String username;
	private String email;
	private String password;
	private Timestamp created_ts;
	private Timestamp updated_ts;
	
/*	public User(){}
	public User(int user_id, String first_name, String last_name,
			String username, String email, String password,
			Timestamp created_ts, Timestamp updated_ts) {
		super();
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.created_ts = created_ts;
		this.updated_ts = updated_ts;
	}*/
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
