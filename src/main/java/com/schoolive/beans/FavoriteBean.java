package com.schoolive.beans;

public class FavoriteBean {
    private int favoriteId;   // 收藏ID
    private int postId;       // 收藏的帖子ID
    private int userId;       // 收藏的用户ID
    private String createdAt; // 收藏时间

    // Getter 和 Setter 方法
    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
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