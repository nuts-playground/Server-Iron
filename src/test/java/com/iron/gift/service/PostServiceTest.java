package com.iron.gift.service;

import com.iron.gift.entiry.Post;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("/글 작성")
	void writePostTest() {
		PostCreate postCreate = PostCreate.builder()
				.title("제목입니다.")
				.content("내용입니다.")
				.build();

		postService.write(postCreate);

		Assertions.assertEquals(1L, postRepository.count());
		Post findPost = postRepository.findAll().get(0);
		Assertions.assertEquals("제목입니다.", findPost.getTitle());
		Assertions.assertEquals("내용입니다.", findPost.getContent());
	}

	@Test
	@DisplayName("글 1개 작성")
	void writePostOneTest() {
		Post post = Post.builder()
				.title("글작성 테스트제목")
				.content("글작성 테스트내용")
				.build();
		postRepository.save(post);

		Post findPost = postService.getPost(post.getId());

		Assertions.assertNotNull(findPost);
		Assertions.assertEquals("글작성 테스트제목", findPost.getTitle());
		Assertions.assertEquals("글작성 테스트내용", findPost.getContent());
	}
}