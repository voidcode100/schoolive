package com.schoolive.dao;

import com.schoolive.beans.CommentBean;
import com.schoolive.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    // 添加评论
    public boolean addComment(CommentBean comment) {
        String sql = "INSERT INTO comments (post_id, user_id, content, created_at) VALUES (?, ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, comment.getPostId()); // 设置帖子ID
            stmt.setInt(2, comment.getUserId()); // 设置用户ID
            stmt.setString(3, comment.getContent()); // 设置评论内容
            return stmt.executeUpdate() > 0; // 执行插入操作
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据帖子ID获取评论列表
    public List<CommentBean> getCommentsByPostId(int postId) {
        String sql = "SELECT c.comment_id, c.post_id, c.user_id, c.content, c.created_at, u.username AS author " +
                     "FROM comments c JOIN users u ON c.user_id = u.user_id " +
                     "WHERE c.post_id = ? ORDER BY c.created_at ASC";
        List<CommentBean> comments = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, postId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CommentBean comment = new CommentBean();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setPostId(rs.getInt("post_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreatedAt(rs.getString("created_at"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    // 删除评论
    public boolean deleteComment(int commentId) {
        String sql = "DELETE FROM comments WHERE comment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, commentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 根据帖子ID删除所有评论
    public boolean deleteCommentsByPostId(int postId) {
        String sql = "DELETE FROM comments WHERE post_id = ?";
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
