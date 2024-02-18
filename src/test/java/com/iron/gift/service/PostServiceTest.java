package com.iron.gift.service;

import com.iron.gift.entiry.Post;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import com.iron.gift.response.PostResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostServiceTest {

	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private MockMvc mockMvc;

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

		PostResponse response = postService.getPost(post.getId());

		Assertions.assertNotNull(response);
		Assertions.assertEquals("글작성 테스트제목", response.getTitle());
		Assertions.assertEquals("글작성 테스트내용", response.getContent());
	}

	@Test
	@DisplayName("글 1개 조회")
	void getPostTest() throws Exception {
		Post post = Post.builder()
				.title("제목입니다.")
				.content("내용입니다.")
				.build();
		postRepository.save(post);

		mockMvc.perform(get("/posts/{postId}", post.getId())
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(post.getId()))
				.andExpect(jsonPath("$.title").value("제목입니다."))
				.andExpect(jsonPath("$.content").value("내용입니다."))
				.andDo(print());
	}

	@Test
	@DisplayName("글 여러개 조회")
	void getPostListTest() throws Exception {
		Post post1 = Post.builder()
				.title("제목입니다1.")
				.content("내용입니다1.")
				.build();
		postRepository.save(post1);
		Post post2 = Post.builder()
				.title("제목입니다2.")
				.content("내용입니다2.")
				.build();
		postRepository.save(post2);

		mockMvc.perform(get("/posts")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", Matchers.is(2)))
				.andExpect(jsonPath("$[0].id").value(post1.getId()))
				.andExpect(jsonPath("$[0].title").value("제목입니다1."))
				.andExpect(jsonPath("$[0].content").value("내용입니다1."))
				.andExpect(jsonPath("$[1].id").value(post2.getId()))
				.andExpect(jsonPath("$[1].title").value("제목입니다2."))
				.andExpect(jsonPath("$[1].content").value("내용입니다2."))
				.andDo(print());
	}


}