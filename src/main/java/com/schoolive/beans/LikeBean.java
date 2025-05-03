package com.schoolive.beans;

public class LikeBean {
    private int likeId;      // 点赞ID
    private int postId;      // 帖子ID
    private int userId;      // 用户ID
    private String createdAt; // 点赞时间

    // Getter 和 Setter 方法
    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
