package db.redis;

public class ItemRanks {
	private int totalCount;
	private int followingCount;
	private float totalAvg;
	private float followingAvg;
	private String userRank;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getFollowingCount() {
		return followingCount;
	}
	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}
	public float getTotalAvg() {
		return totalAvg;
	}
	public void setTotalAvg(float totalAvg) {
		this.totalAvg = totalAvg;
	}
	public float getFollowingAvg() {
		return followingAvg;
	}
	public void setFollowingAvg(float followingAvg) {
		this.followingAvg = followingAvg;
	}
	public String getUserRank() {
		return userRank;
	}
	public void setUserRank(String userRank) {
		this.userRank = userRank;
	}
	
}
