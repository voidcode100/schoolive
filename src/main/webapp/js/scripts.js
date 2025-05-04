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
  const icon = button.querySelector(".like-icon");
  const count = button.querySelector(".like-count");
  const isLiked = button.classList.contains("liked");
  const postId = button.dataset.postId;

  fetch(`/schoolive/like?postId=${postId}`, {
    method: isLiked ? "DELETE" : "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        if (isLiked) {
          button.classList.remove("liked");
          icon.src = "images/like-48.png"; // 未点赞图片
        } else {
          button.classList.add("liked");
          icon.src = "images/like-49.png"; // 已点赞图片
        }
        count.textContent = data.likes; // 更新点赞数
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
  const icon = button.querySelector(".favorite-icon");
  const isFavorited = button.classList.contains("favorited");
  const postId = button.dataset.postId;

  fetch(`/schoolive/favorite?postId=${postId}`, {
    method: isFavorited ? "DELETE" : "POST",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        if (isFavorited) {
          button.classList.remove("favorited");
          icon.src = "images/favorite-41.png"; // 未收藏图片
        } else {
          button.classList.add("favorited");
          icon.src = "images/favorite-40.png"; // 已收藏图片
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
  event.preventDefault(); // 阻止表单默认提交行为

  const commentInput = document.getElementById("commentInput"); // 获取评论输入框
  const commentList = document.getElementById("commentList"); // 获取评论列表容器
  const postId = commentInput.dataset.postId; // 从 data-post-id 属性获取帖子 ID

  if (!postId) {
    alert("无法获取帖子 ID，请刷新页面重试！");
    return;
  }

  const commentContent = commentInput.value.trim(); // 获取输入框中的评论内容
  if (commentContent === "") {
    alert("评论内容不能为空！");
    return;
  }

  // 发送评论请求到后端
  fetch(`/schoolive/comment`, {
    method: "POST",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `postId=${postId}&content=${encodeURIComponent(commentContent)}`,
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        // 创建新的评论元素并添加到评论列表
        const newComment = document.createElement("li");
        newComment.innerHTML = `<p>${data.author}: ${commentContent}</p>`;
        commentList.appendChild(newComment);

        // 清空输入框
        commentInput.value = "";
      } else {
        alert("评论提交失败，请稍后重试！");
      }
    })
    .catch((error) => {
      console.error("评论提交失败：", error);
      alert("网络错误，请稍后重试！");
    });
}

// 绑定评论提交事件
const commentForm = document.getElementById("commentForm");
if (commentForm) {
  commentForm.addEventListener("submit", submitComment);
}

// 动态加载帖子列表
function loadPosts() {
  const postList = document.getElementById("postList");

  fetch("/schoolive/posts")
    .then((response) => response.json())
    .then((posts) => {
      posts.forEach((post) => {
        const postElement = document.createElement("div");
        postElement.className = "post";
        postElement.innerHTML = `
          <h3><a href="post.jsp?postId=${post.postId}">${post.title}</a></h3>
          <p>${post.content.substring(0, 100)}...</p>
          <p><small>发布者：${post.author} | 发布时间：${post.createdAt}</small></p>
          <div class="post-actions">
            <button class="like-button ${post.isLiked ? 'liked' : ''}" data-post-id="${post.postId}">
              <img class="like-icon" src="images/${post.isLiked ? 'like-49.png' : 'like-48.png'}" />
              <span class="like-count">${post.likes}</span>
            </button>
            <button class="favorite-button ${post.isFavorited ? 'favorited' : ''}" data-post-id="${post.postId}">
              <img class="favorite-icon" src="images/${post.isFavorited ? 'favorite-40.png' : 'favorite-41.png'}" />
            </button>
            <div class="comment-count">
              <img class="comment-icon" src="images/comments-50.png" />
              <span>${post.comments}</span>
            </div>
          </div>
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

  fetch(`/schoolive/comment?postId=${postId}`)
    .then((response) => response.json())
    .then((comments) => {
      comments.forEach((comment) => {
        const commentElement = document.createElement("li");
        commentElement.className = "comment";
        commentElement.innerHTML = `
          <p>
            <a href="userProfile.jsp?userId=${comment.userId}" class="comment-author">${comment.author}</a>: 
            ${comment.content}
          </p>
          <p><small>评论时间：${comment.createdAt}</small></p>
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
  fetch(`/schoolive/postDetail?postId=${postId}`)
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

// 返回到帖子详细页面
function goBackToPost() {
  const referrer = document.referrer; // 获取来源页面
  if (referrer && referrer.includes("post.jsp")) {
    window.location.href = referrer; // 返回来源页面
  } else {
    window.location.href = "post.jsp"; // 如果没有来源页面，跳转到 post.jsp
  }
}

// 切换展开/收起功能
function toggleSection(sectionId, button) {
  const section = document.getElementById(sectionId);
  const icon = button.querySelector(".toggle-icon");

  if (section.style.display === "none" || section.style.display === "") {
    section.style.display = "block";
    icon.src = "images/fold-48.png"; // 展开图标
  } else {
    section.style.display = "none";
    icon.src = "images/fold-49.png"; // 收起图标
  }
}

// 动态加载登陆用户帖子
function loadNowUserPosts() {
  const userPostList = document.getElementById("userPostList");

  fetch("/schoolive/userPosts")
    .then((response) => response.json())
    .then((posts) => {
      posts.forEach((post) => {
        const postElement = document.createElement("div");
        postElement.className = "post";
        postElement.innerHTML = `
          <h3><a href="post.jsp?postId=${post.postId}">${post.title}</a></h3>
          <p>${post.content.substring(0, 100)}...</p>
          <p><small>发布时间：${post.createdAt}</small></p>
          <button class="delete-post-button" data-post-id="${post.postId}">
            <img class="delete-icon" src="images/delete-40.png" alt="删除帖子" />
          </button>
        `;
        userPostList.appendChild(postElement);

        // 绑定删除帖子事件
        const deleteButton = postElement.querySelector(".delete-post-button");
        deleteButton.addEventListener("click", () => deletePost(deleteButton));
      });
    })
    .catch((error) => {
      console.error("加载用户帖子失败：", error);
      alert("无法加载用户帖子，请稍后重试！");
    });
}

// 删除帖子
function deletePost(button) {
  const postId = button.dataset.postId;

  if (confirm("确定要删除这篇帖子吗？")) {
    fetch(`/schoolive/posts?postId=${postId}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          button.parentElement.remove(); // 删除帖子元素
          alert("帖子已删除！");

          // 重新加载收藏帖子列表
          const favoritePostList = document.getElementById("favoritePostList");
          if (favoritePostList) {
            favoritePostList.innerHTML = ""; // 清空当前收藏帖子列表
            loadFavoritePosts(); // 重新加载收藏帖子
          }
        } else {
          alert("删除失败，请稍后重试！");
        }
      })
      .catch((error) => {
        console.error("删除帖子失败：", error);
        alert("网络错误，请稍后重试！");
      });
  }
}

// 动态加载登陆用户收藏帖子
function loadFavoritePosts() {
  const favoritePostList = document.getElementById("favoritePostList");

  fetch("/schoolive/favoritePosts")
    .then((response) => response.json())
    .then((posts) => {
      posts.forEach((post) => {
        const postElement = document.createElement("div");
        postElement.className = "post";
        postElement.innerHTML = `
          <h3><a href="post.jsp?postId=${post.postId}">${post.title}</a></h3>
          <p>${post.content.substring(0, 100)}...</p>
          <p><small>发布时间：${post.createdAt}</small></p>
          <button class="cancel-favorite-button" data-post-id="${post.postId}">
            <img class="delete-icon" src="images/delete-40.png" alt="取消收藏" />
          </button>
        `;
        favoritePostList.appendChild(postElement);

        // 绑定取消收藏事件
        const cancelButton = postElement.querySelector(".cancel-favorite-button");
        cancelButton.addEventListener("click", () => cancelFavorite(cancelButton));
      });
    })
    .catch((error) => {
      console.error("加载收藏帖子失败：", error);
      alert("无法加载收藏帖子，请稍后重试！");
    });
}

// 取消收藏
function cancelFavorite(button) {
  const postId = button.dataset.postId;

  fetch(`/schoolive/favorite?postId=${postId}`, {
    method: "DELETE",
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.success) {
        button.parentElement.remove(); // 删除收藏帖子元素
        alert("已取消收藏！");
      } else {
        alert("取消收藏失败，请稍后重试！");
      }
    })
    .catch((error) => {
      console.error("取消收藏失败：", error);
      alert("网络错误，请稍后重试！");
    });
}

// 动态加载用户信息
function loadUserProfile(userId) {
  fetch(`/schoolive/userProfile?userId=${userId}`)
    .then((response) => response.json())
    .then((user) => {
      document.getElementById("username").textContent = user.username;
      document.getElementById("email").textContent = user.email;
      document.getElementById("bio").textContent = user.bio || "暂无简介";
    })
    .catch((error) => {
      console.error("加载用户信息失败：", error);
      alert("无法加载用户信息，请稍后重试！");
    });
}

// 动态加载用户帖子
function loadUserPosts(userId) {
  const userPostList = document.getElementById("userPostList");

  fetch(`/schoolive/userPosts?userId=${userId}`)
    .then((response) => response.json())
    .then((posts) => {
      posts.forEach((post) => {
        const postElement = document.createElement("div");
        postElement.className = "post";
        postElement.innerHTML = `
          <h3><a href="post.jsp?postId=${post.postId}">${post.title}</a></h3>
          <p>${post.content.substring(0, 100)}...</p>
          <p><small>发布时间：${post.createdAt}</small></p>
        `;
        userPostList.appendChild(postElement);
      });
    })
    .catch((error) => {
      console.error("加载用户帖子失败：", error);
      alert("无法加载用户帖子，请稍后重试！");
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

  // 判断是否在 profile.jsp 页面
  if (document.body.id === "profile-page") {
    loadNowUserPosts(); // 加载登录用户的帖子
    loadFavoritePosts(); // 加载登录用户的收藏帖子
  }

  // 判断是否在 userProfile.jsp 页面
  if (document.body.id === "user-profile-page") {
    const userId = document.body.dataset.userId; // 从 body 的 data-user-id 属性获取用户 ID
    loadUserProfile(userId); // 加载指定用户信息
    loadUserPosts(userId); // 加载指定用户的帖子
  }
});