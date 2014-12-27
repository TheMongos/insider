package db.redis;

public class ItemRanks {
	private int totalCount;
	private int followingCount;
	private int totalAvg;
	private int followingAvg;
	private int userRank;
	
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
	public int getTotalAvg() {
		return totalAvg;
	}
	public void setTotalAvg(int totalAvg) {
		this.totalAvg = totalAvg;
	}
	public int getFollowingAvg() {
		return followingAvg;
	}
	public void setFollowingAvg(int followingAvg) {
		this.followingAvg = followingAvg;
	}
	public int getUserRank() {
		return userRank;
	}
	public void setUserRank(int userRank) {
		this.userRank = userRank;
	}
	
}
