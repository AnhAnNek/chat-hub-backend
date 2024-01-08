package com.vanannek.socialmedia.post;

import com.vanannek.socialmedia.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Post")
public class PostController {

    private static final Logger log = LogManager.getLogger(PostController.class);

    @Autowired private PostService postService;

    @Operation(
            summary = "Add a Post",
            description = "Add a new post by providing the necessary details in the request body.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("/add-post")
    public ResponseEntity<String> add(@RequestBody PostDTO postDTO) {
        postService.save(postDTO);
        log.info(PostUtils.POST_ADDED_SUCCESSFULLY);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PostUtils.POST_ADDED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Update a Post",
            description = "Update a post by providing the necessary details in the request body.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @PutMapping("/update-post")
    public ResponseEntity<String> update(@RequestBody UpdatePostRequest updateRequest) {
        postService.update(updateRequest);
        log.info(PostUtils.POST_UPDATED_SUCCESSFULLY);
        return ResponseEntity.ok(PostUtils.POST_UPDATED_SUCCESSFULLY);
    }

    @Operation(
            summary = "Retrieve News Feed",
            description = "Retrieve a user's news feed by providing the username, page, and size as query parameters.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
    @GetMapping("/get-news-feed/{username}")
    public List<PostDTO> getNewsFeed(
            @PathVariable("username") String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PostDTO> newsFeed = postService.getNewsFeed(username, page, size);
        log.info(PostUtils.NEWS_FEED_RETRIEVED_SUCCESSFULLY);
        return newsFeed;
    }

    @Operation(
            summary = "Retrieve User's Posts",
            description = "Retrieve posts created by a user by providing the username, page, and size as query parameters.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401"
                    )
            }
    )
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