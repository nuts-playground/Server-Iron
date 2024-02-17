package com.iron.gift.controller;

import com.iron.gift.entiry.Post;
import com.iron.gift.request.PostCreate;
import com.iron.gift.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;

	@PostMapping("/posts")
	public void post(@RequestBody @Valid PostCreate postCreate) {
		postService.write(postCreate);
	}

	@GetMapping("/posts/{postId}")
	public Post get(@PathVariable(name = "postId") Long id) {
		return postService.getPost(id);
	}
}

