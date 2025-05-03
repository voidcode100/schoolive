package com.schoolive.dao;

import com.schoolive.beans.FavoriteBean;
import com.schoolive.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDao {

    // 添加收藏
    public boolean addFavorite(FavoriteBean favorite) {
        String sql = "INSERT INTO favorites (post_id, user_id, created_at) VALUES (?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, favorite.getPostId());
            stmt.setInt(2, favorite.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 取消收藏
    public boolean removeFavorite(int postId, int userId) {
        String sql = "DELETE FROM favorites WHERE post_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 检查用户是否已收藏
    public boolean isFavorited(int postId, int userId) {
        String sql = "SELECT COUNT(*) FROM favorites WHERE post_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 获取用户收藏的帖子列表
    public List<FavoriteBean> getFavoritePostsByUserId(int userId) {
        String sql = "SELECT f.favorite_id, f.post_id, f.user_id, f.created_at, p.title, p.content, p.created_at AS post_created_at " +
                     "FROM favorites f JOIN posts p ON f.post_id = p.post_id WHERE f.user_id = ? ORDER BY f.created_at DESC";
        List<FavoriteBean> favorites = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                FavoriteBean favorite = new FavoriteBean();
                favorite.setFavoriteId(rs.getInt("favorite_id"));
                favorite.setPostId(rs.getInt("post_id"));
                favorite.setUserId(rs.getInt("user_id"));
                favorite.setCreatedAt(rs.getString("created_at"));
                favorites.add(favorite);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorites;
    }

    // 根据帖子ID删除所有收藏
    public boolean removeFavoritesByPostId(int postId) {
        String sql = "DELETE FROM favorites WHERE post_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
