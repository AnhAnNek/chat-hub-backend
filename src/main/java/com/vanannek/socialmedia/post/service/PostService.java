package com.vanannek.socialmedia.post.service;

import com.vanannek.socialmedia.post.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO save(PostDTO postDTO);
    PostDTO savePostWithReactionsAndComments(PostDTO postDTO);
    PostDTO update(PostDTO postDTO);
    List<PostDTO> getNewsFeed(String username, int page, int size);
    List<PostDTO> getMyPosts(String username, int page, int size);
}
