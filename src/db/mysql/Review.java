package db.mysql;

import java.sql.Timestamp;

public class Review {
	private int review_id;
	private int user_id;
	private int item_id;
	//private byte rank;
	private String review_text;
	private Timestamp created_ts;
	private Timestamp updated_ts;
	
/*	public Review(){};
	
	public Review(int review_id, int user_id, int item_id, byte category,
			byte rank, String review_text, Timestamp created_ts,
			Timestamp updated_ts) {
		super();
		this.review_id = review_id;
		this.user_id = user_id;
		this.item_id = item_id;
		this.category = category;
		this.rank = rank;
		this.review_text = review_text;
		this.created_ts = created_ts;
		this.updated_ts = updated_ts;
	}
*/
	public int getReview_id() {
		return review_id;
	}
	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
//	public byte getRank() {
//		return rank;
//	}
//	public void setRank(byte rank) {
//		this.rank = rank;
//	}
	public String getReview_text() {
		return review_text;
	}
	public void setReview_text(String review_text) {
		this.review_text = review_text;
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
