// 表单验证
function validateForm(event) {
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();

  if (username === "" || password === "") {
    alert("用户名和密码不能为空！");
    event.preventDefault(); // 阻止表单提交
  }
}

// 绑定表单验证事件
const loginForm = document.getElementById("loginForm");
if (loginForm) {
  loginForm.addEventListener("submit", validateForm);
}

// 点赞功能
function toggleLike(button) {
  const isLiked = button.classList.contains("liked");
  const postId = button.dataset.postId;

  fetch(`/like?postId=${postId}`, {
    method: isLiked ? "DELETE" : "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        if (isLiked) {
          button.classList.remove("liked");
          button.textContent = `点赞 (${data.likes})`;
        } else {
          button.classList.add("liked");
          button.textContent = `已点赞 (${data.likes})`;
        }
      } else {
        alert("操作失败，请稍后重试！");
      }
    })
    .catch((error) => {
      console.error("点赞请求失败：", error);
      alert("网络错误，请稍后重试！");
    });
}

// 绑定点赞按钮事件
const likeButtons = document.querySelectorAll(".like-button");
likeButtons.forEach((button) => {
  button.addEventListener("click", () => toggleLike(button));
});

// 收藏功能
function toggleFavorite(button) {
  const isFavorited = button.classList.contains("favorited");
  const postId = button.dataset.postId;

  fetch(`/favorite?postId=${postId}`, {
    method: isFavorited ? "DELETE" : "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        if (isFavorited) {
          button.classList.remove("favorited");
          button.textContent = "收藏";
        } else {
          button.classList.add("favorited");
          button.textContent = "已收藏";
        }
      } else {
        alert("操作失败，请稍后重试！");
      }
    })
    .catch((error) => {
      console.error("收藏请求失败：", error);
      alert("网络错误，请稍后重试！");
    });
}

// 绑定收藏按钮事件
const favoriteButtons = document.querySelectorAll(".favorite-button");
favoriteButtons.forEach((button) => {
  button.addEventListener("click", () => toggleFavorite(button));
});

// 提交评论
function submitComment(event) {
  event.preventDefault();
  const commentInput = document.getElementById("commentInput");
  const commentList = document.getElementById("commentList");
  const postId = commentInput.dataset.postId;

  if (commentInput.value.trim() !== "") {
    fetch(`/comment?postId=${postId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ content: commentInput.value.trim() }),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          const newComment = document.createElement("li");
          newComment.textContent = `${data.author}: ${commentInput.value}`;
          commentList.appendChild(newComment);
          commentInput.value = ""; // 清空输入框
        } else {
          alert("评论提交失败，请稍后重试！");
        }
      })
      .catch((error) => {
        console.error("评论提交失败：", error);
        alert("网络错误，请稍后重试！");
      });
  } else {
    alert("评论内容不能为空！");
  }
}

// 绑定评论提交事件
const commentForm = document.getElementById("commentForm");
if (commentForm) {
  commentForm.addEventListener("submit", submitComment);
}

// 关注功能
function toggleFollow(button) {
  const isFollowing = button.classList.contains("following");
  const followeeId = button.dataset.followeeId;

  fetch(`/follow?followeeId=${followeeId}`, {
    method: isFollowing ? "DELETE" : "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        if (isFollowing) {
          button.classList.remove("following");
          button.textContent = "关注";
        } else {
          button.classList.add("following");
          button.textContent = "已关注";
        }
      } else {
        alert("操作失败，请稍后重试！");
      }
    })
    .catch((error) => {
      console.error("关注请求失败：", error);
      alert("网络错误，请稍后重试！");
    });
}

// 绑定关注按钮事件
const followButtons = document.querySelectorAll(".follow-button");
followButtons.forEach((button) => {
  button.addEventListener("click", () => toggleFollow(button));
});

// 动态加载帖子列表
function loadPosts() {
  const postList = document.getElementById("postList");

  fetch("/posts")
    .then((response) => response.json())
    .then((posts) => {
      posts.forEach((post) => {
        const postElement = document.createElement("div");
        postElement.className = "post";
        postElement.innerHTML = `
          <h3><a href="post.jsp?postId=${post.postId}">${post.title}</a></h3>
          <p>${post.content.substring(0, 100)}...</p>
          <p><small>作者：${post.author} | 发布时间：${post.createdAt}</small></p>
          <button class="like-button" data-post-id="${post.postId}">点赞 (${post.likes})</button>
          <button class="favorite-button" data-post-id="${post.postId}">收藏</button>
        `;
        postList.appendChild(postElement);

        // 绑定点赞和收藏事件
        const likeButton = postElement.querySelector(".like-button");
        likeButton.addEventListener("click", () => toggleLike(likeButton));

        const favoriteButton = postElement.querySelector(".favorite-button");
        favoriteButton.addEventListener("click", () => toggleFavorite(favoriteButton));
      });
    })
    .catch((error) => {
      console.error("加载帖子失败：", error);
      alert("无法加载帖子，请稍后重试！");
    });
}

// 加载评论
function loadComments(postId) {
  const commentList = document.getElementById("commentList");

  fetch(`/comments?postId=${postId}`)
    .then((response) => response.json())
    .then((comments) => {
      comments.forEach((comment) => {
        const commentElement = document.createElement("li");
        commentElement.className = "comment";
        commentElement.innerHTML = `
          <p>${comment.content}</p>
          <p><small>评论者：${comment.author} | 评论时间：${comment.createdAt}</small></p>
        `;
        commentList.appendChild(commentElement);
      });
    })
    .catch((error) => {
      console.error("加载评论失败：", error);
      alert("无法加载评论，请稍后重试！");
    });
}

// 加载帖子详情
function loadPostDetails(postId) {
  fetch(`/postDetail?postId=${postId}`)
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error("无法加载帖子详情");
      }
    })
    .then((post) => {
      document.querySelector("article").innerHTML = `
        <h2>${post.title}</h2>
        <p>${post.content}</p>
        <p><small>作者：${post.author} | 发布时间：${post.createdAt}</small></p>
      `;
    })
    .catch((error) => {
      console.error("加载帖子详情失败：", error);
      alert("无法加载帖子详情，请稍后重试！");
    });
}

// 加载用户信息
function loadUserProfile() {
  fetch("/profile")
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error("未登录或无法加载用户信息");
      }
    })
    .then((user) => {
      document.querySelector(".profile-info").innerHTML = `
        <p><strong>用户名：</strong> ${user.username}</p>
        <p><strong>邮箱：</strong> ${user.email}</p>
        <p><strong>简介：</strong> ${user.bio}</p>
      `;
    })
    .catch((error) => {
      console.error("加载用户信息失败：", error);
      alert("无法加载用户信息，请稍后重试！");
    });
}

// 页面加载时初始化
document.addEventListener("DOMContentLoaded", () => {
  const postList = document.getElementById("postList");
  if (postList) {
    loadPosts();
  }

  const commentList = document.getElementById("commentList");
  if (commentList) {
    const postId = commentList.dataset.postId;
    loadPostDetails(postId);
    loadComments(postId);
  }

  loadUserProfile();
});