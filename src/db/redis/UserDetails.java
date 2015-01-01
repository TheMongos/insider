package db.redis;

public class UserDetails {
	int userFollowingCount;
	int userFollowersCount;
	Boolean isFollowing;
	
	public Boolean getIsFollowing() {
		return isFollowing;
	}
	public void setIsFollowing(Boolean isFollowing) {
		this.isFollowing = isFollowing;
	}
	public int getUserFollowingCount() {
		return userFollowingCount;
	}
	public void setUserFollowingCount(int userFollowingCount) {
		this.userFollowingCount = userFollowingCount;
	}
	public int getUserFollowersCount() {
		return userFollowersCount;
	}
	public void setUserFollowersCount(int userFollowersCount) {
		this.userFollowersCount = userFollowersCount;
	}
	
}
