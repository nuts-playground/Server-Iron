package com.iron.gift.controller;

import com.iron.gift.config.data.UserSession;
import com.iron.gift.request.post.PostCreate;
import com.iron.gift.request.post.PostEdit;
import com.iron.gift.request.post.PostSearch;
import com.iron.gift.response.PostResponse;
import com.iron.gift.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

	private final PostService postService;

	@GetMapping("/foo")
	public Long foo(UserSession userSession) {
		log.info("foo>>> : {}", userSession.id);
		return userSession.id;
	}

	@GetMapping("/bar")
	public String foo() {
		return "인증이 필요없는 페이지";
	}


	@PostMapping("/posts")
	public Long post(@RequestBody @Valid PostCreate request) {

		request.validate();
		return postService.write(request);
	}


	@GetMapping("/posts/{postId}")
	public PostResponse get(@PathVariable(name = "postId") Long id) {
		return postService.getPost(id);
	}

	@GetMapping("/posts")
	public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
		return postService.getList(postSearch);
	}

	@PatchMapping("/posts/{postId}")
	public PostResponse edit(@PathVariable(name = "postId") Long id, @RequestBody @Valid PostEdit postEdit) {
		return postService.editPost(id, postEdit).toResponse();
	}

	@DeleteMapping("/posts/{postId}")
	public void delete(@PathVariable(name = "postId") Long id) {
		postService.deletePost(id);
	}

}



