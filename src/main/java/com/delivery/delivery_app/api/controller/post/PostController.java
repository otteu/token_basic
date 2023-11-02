package com.delivery.delivery_app.api.controller.post;

import com.delivery.delivery_app.api.controller.common.ApiResponse;
import com.delivery.delivery_app.api.controller.post.request.PostCreateRequest;
import com.delivery.delivery_app.api.controller.post.request.PostModifyRequest;
import com.delivery.delivery_app.api.controller.post.response.LikesCountResponse;
import com.delivery.delivery_app.api.controller.post.response.PostResponse;
import com.delivery.delivery_app.api.service.post.PostService;
import com.delivery.delivery_app.api.service.post.model.Post;
import com.delivery.delivery_app.api.service.post.model.PostComment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<Void> create(@RequestBody PostCreateRequest request, Authentication authentication) {
        postService.create(request.getTitle(), request.getBody(), authentication.getName());
        return ApiResponse.success();
    }

    @PutMapping("{postId}")
    public ApiResponse<PostResponse> modify(@PathVariable Long postId, @RequestBody PostModifyRequest request, Authentication authentication) {
        Post post = postService.modify(request.getTitle(), request.getBody(), authentication.getName(), postId);
        return ApiResponse.success(PostResponse.of(post));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Void> delete(@PathVariable Long postId, Authentication authentication) {
        postService.delete(authentication.getName(), postId);
        return ApiResponse.success();
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> list(@PageableDefault(size = 10, sort = "post_id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Pageable pageable = PageRequest.of(0,10);


        return ApiResponse.success(postService.list(pageable).map(PostResponse::of));
    }

    @GetMapping("/my")
    public ApiResponse<Page<PostResponse>> my(Pageable pageable, Authentication authentication) {
        return ApiResponse.success(postService.my(authentication.getName(), pageable).map(PostResponse::of));
    }


    @PostMapping("/{postId}/likes")
    public ApiResponse<Void> like(@PathVariable Long postId, Authentication authentication) {

        postService.like(postId, authentication.getName());

        return ApiResponse.success();
    }

    @GetMapping("/{postId}/likes")
    public ApiResponse<LikesCountResponse> likeCount(@PathVariable Long postId, Authentication authentication) {
        Long likeCount = postService.likeCount(postId);
        return ApiResponse.success(LikesCountResponse.of(likeCount));
    }

    @PostMapping("/{postId}/comments")
    public ApiResponse<Void> comment(@PathVariable Long postId, @RequestBody PostCommentRequest request, Authentication authentication) {

        postService.comment(postId, authentication.getName(), request.getComment());

        return ApiResponse.success();
    }

    @GetMapping("/{postId}/comments")
    public ApiResponse<Page<PostComment>> comment(@PathVariable Long postId, Authentication authentication) {
        Pageable pageable = PageRequest.of(0,10);
        Page<PostComment> findComment = postService.getComment(postId, pageable);

        return ApiResponse.success(findComment);
    }



}
























