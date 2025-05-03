package com.schoolive.dao;

import com.schoolive.beans.PostBean;
import com.schoolive.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    // 发布帖子
    public boolean createPost(PostBean post) {
        String sql = "INSERT INTO posts (user_id, title, content, created_at) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getTitle());
            stmt.setString(3, post.getContent());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取所有帖子（包含点赞数和用户是否已点赞、已收藏）
    public List<PostBean> getAllPostsWithLikesAndFavorites(int userId) {
        String sql = "SELECT p.post_id, p.user_id, p.title, p.content, p.created_at, p.updated_at, u.username AS author, " +
                     "(SELECT COUNT(*) FROM likes l WHERE l.post_id = p.post_id) AS likes, " +
                     "(SELECT COUNT(*) FROM likes l WHERE l.post_id = p.post_id AND l.user_id = ?) AS isLiked, " +
                     "(SELECT COUNT(*) FROM favorites f WHERE f.post_id = p.post_id AND f.user_id = ?) AS isFavorited " +
                     "FROM posts p JOIN users u ON p.user_id = u.user_id ORDER BY p.created_at DESC";
        List<PostBean> posts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PostBean post = new PostBean();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getString("created_at"));
                post.setUpdatedAt(rs.getString("updated_at"));
                post.setAuthor(rs.getString("author"));
                post.setLikes(rs.getInt("likes"));
                post.setLiked(rs.getInt("isLiked") > 0);
                post.setFavorited(rs.getInt("isFavorited") > 0); // 判断是否已收藏
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    // 根据帖子ID获取帖子详情
    public PostBean getPostById(int postId) {
        String sql = "SELECT p.post_id, p.user_id, p.title, p.content, p.created_at, p.updated_at, u.username AS author " +
                     "FROM posts p JOIN users u ON p.user_id = u.user_id WHERE p.post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PostBean post = new PostBean();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getString("created_at"));
                post.setUpdatedAt(rs.getString("updated_at"));
                return post;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新帖子
    public boolean updatePost(PostBean post) {
        String sql = "UPDATE posts SET title = ?, content = ?, updated_at = NOW() WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, post.getTitle());
            stmt.setString(2, post.getContent());
            stmt.setInt(3, post.getPostId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除帖子
    public boolean deletePost(int postId) {
        String sql = "DELETE FROM posts WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据用户ID获取帖子
    public List<PostBean> getPostsByUserId(int userId) {
        String sql = "SELECT post_id, user_id, title, content, created_at, updated_at FROM posts WHERE user_id = ? ORDER BY created_at DESC";
        List<PostBean> posts = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PostBean post = new PostBean();
                post.setPostId(rs.getInt("post_id"));
                post.setUserId(rs.getInt("user_id"));
                post.setTitle(rs.getString("title"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getString("created_at"));
                post.setUpdatedAt(rs.getString("updated_at"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
