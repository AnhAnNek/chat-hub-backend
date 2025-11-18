package com.vanannek.socialmedia.post.service;

import com.vanannek.socialmedia.post.Post;
import com.vanannek.socialmedia.post.PostDTO;
import com.vanannek.socialmedia.post.UpdatePostRequest;

import java.util.List;

public interface PostService {
    PostDTO save(PostDTO postDTO);
    PostDTO savePostWithReactionsAndComments(PostDTO postDTO);
    PostDTO update(UpdatePostRequest updateRequest);
    Post getPostById(Long postId);
    List<PostDTO> getNewsFeed(String username, int page, int size);
    List<PostDTO> getMyPosts(String username, int page, int size);
}
