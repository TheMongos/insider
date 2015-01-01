package utils;

import db.mysql.User;
import db.redis.UserDetails;

public class UserRes {
	private User user;
	private UserDetails userDetails;
	
	 public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
}
