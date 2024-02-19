package com.iron.gift.controller;

import com.iron.gift.request.PostCreate;
import com.iron.gift.response.PostResponse;
import com.iron.gift.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/posts")
	public Long post(@RequestBody @Valid PostCreate postCreate) {
		return postService.write(postCreate);
	}


	@GetMapping("/posts/{postId}")
	public PostResponse get(@PathVariable(name = "postId") Long id) {
		return postService.getPost(id);
	}

	@GetMapping("/posts")
	public List<PostResponse> getList(@PageableDefault(size = 5) Pageable pageable) {
		return postService.getList(pageable);
	}
}

