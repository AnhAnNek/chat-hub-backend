package com.vanannek.socialmedia.post;

import com.vanannek.socialmedia.post.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private static final Logger log = LogManager.getLogger(PostController.class);

    @Autowired private PostService postService;

    @PostMapping("/add-post")
    public ResponseEntity<String> add(@RequestBody PostDTO postDTO) {
        postService.save(postDTO);
        log.info(PostUtils.POST_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostUtils.POST_ADDED_SUCCESSFULLY);
    }

    @PutMapping("/update-post")
    public ResponseEntity<String> update(@RequestBody PostDTO postDTO) {
        postService.update(postDTO);
        log.info(PostUtils.POST_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(PostUtils.POST_UPDATED_SUCCESSFULLY);
    }

    @GetMapping("/get-news-feed/{username}")
    public List<PostDTO> getNewsFeed(
            @PathVariable("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PostDTO> newsFeed = postService.getNewsFeed(username, page, size);
        log.info(PostUtils.NEWS_FEED_RETRIEVED_SUCCESSFULLY);
        return newsFeed;
    }

    @GetMapping("/get-my-posts/{username}")
    public List<PostDTO> getMyPosts(
            @PathVariable("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PostDTO> userPosts = postService.getMyPosts(username, page, size);
        log.info(PostUtils.USER_POSTS_RETRIEVED_SUCCESSFULLY);
        return userPosts;
    }
}