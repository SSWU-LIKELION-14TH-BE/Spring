package com.example.project.paging_transaction.controller;

import com.example.project.apiPayload.dto.ApiResponse;
import com.example.project.paging_transaction.dto.PostResponse;
import com.example.project.paging_transaction.entity.Post;
import com.example.project.paging_transaction.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public ApiResponse<List<PostResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findAll(pageable);

        List<PostResponse> responseList = postPage.getContent()
                .stream()
                .map(PostResponse::from)
                .toList();

        return ApiResponse.onSuccess(responseList);
    }
}