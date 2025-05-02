package com.schoolive.beans;

public class FollowBean {
    private int followId;        // 关注ID
    private int followerId;      // 关注者ID
    private int followeeId;      // 被关注者ID
    private String createdAt;    // 关注时间

    // Getter 和 Setter 方法
    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(int followeeId) {
        this.followeeId = followeeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
