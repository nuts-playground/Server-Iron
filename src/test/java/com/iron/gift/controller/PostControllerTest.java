package com.iron.gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iron.gift.domain.Post;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import com.iron.gift.request.PostEdit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostRepository postRepository;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("/hello 요청 시 Hello World를 출력한다.")
	void helloWorldTest() throws Exception {

		mockMvc.perform(get("/hello")
						.accept(APPLICATION_JSON)
						.param("param", "World"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World"));
	}

	@Test
	@DisplayName("/posts 요청 시 title 값은 필수다.")
	@Transactional
	void titleNotNullTest() throws Exception {
		PostCreate postCreate = PostCreate.builder()
				.content("내용입니다.")
				.build();

		String json = objectMapper.writeValueAsString(postCreate);

		mockMvc.perform(post("/posts")
						.content(json)
						.contentType(APPLICATION_JSON)
				)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
				.andExpect(jsonPath("$.validation.title").value("제목은 필수입력입니다."))
				.andDo(print());
	}

	@Test
	@DisplayName("/posts 요청 시 DB에 값이 저장된다.")
	@Transactional
	void writePostTest() throws Exception {
		long originalCnt = postRepository.count();
		PostCreate postCreate = PostCreate.builder()
				.title("제목입니다.")
				.content("내용입니다.")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(postCreate);

		mockMvc.perform(post("/posts")
						.content(json)
						.contentType(APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andDo(print());

		assertEquals(originalCnt+1, postRepository.count());

		Post post = postRepository.findAll().get(0);
		assertEquals("제목입니다.", post.getTitle());
		assertEquals("내용입니다.", post.getContent());
	}

	@Test
	@Transactional
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
	@Transactional
	@DisplayName("글 여러개 조회")
	void getList() throws Exception {
		List<Post> requestPosts = IntStream.range(1, 31)
				.mapToObj(i ->
						Post.builder()
								.title("제목 - " + i)
								.content("내용 - " + i)
								.build())
				.collect(Collectors.toList());

		postRepository.saveAll(requestPosts);

		mockMvc.perform(get("/posts?page=1&size=10")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(10)))
				.andExpect(jsonPath("$[0].title").value("제목 - 30"))
				.andExpect(jsonPath("$[0].content").value("내용 - 30"))
				.andDo(print());

	}

	@Test
	@Transactional
	@DisplayName("페이지를 0으로 요청해도 첫 페이지를 가져온다.")
	void getListDefaultPage() throws Exception {
		List<Post> requestPosts = IntStream.range(1, 31)
				.mapToObj(i ->
						Post.builder()
								.title("제목 - " + i)
								.content("내용 - " + i)
								.build())
				.collect(Collectors.toList());

		postRepository.saveAll(requestPosts);

		mockMvc.perform(get("/posts?page=0&size=10")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(10)))
				.andExpect(jsonPath("$[0].title").value("제목 - 30"))
				.andExpect(jsonPath("$[0].content").value("내용 - 30"))
				.andDo(print());

	}


	@Test
	@DisplayName("글 수정")
	@Transactional
	void editPost() throws Exception {
		Post post = Post.builder()
				.title("원래 제목")
				.content("원래 내용")
				.build();
		postRepository.save(post);

		PostEdit editPost = PostEdit.builder()
				.title("수정 제목")
				.content("수정 내용")
				.build();

		mockMvc.perform(patch("/posts/{postId}", post.getId())    // PATCH /posts/{postId}
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(editPost)))
				.andExpect(status().isOk())
				.andDo(print());

	}

	@Test
	@Transactional
	@DisplayName("글 삭제")
	void deletePost() throws Exception {
		Post post = Post.builder()
				.title("글제목")
				.content("글내용")
				.build();
		postRepository.save(post);

		mockMvc.perform(delete("/posts/{postId}", post.getId())
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	@DisplayName("존재하지 않는 글 조회")
	void getNotFoundPost() throws Exception {
		mockMvc.perform(delete("/posts/{postId}", 1L)
						.contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	@DisplayName("존재하지 않는 글 수정")
	void editNotFoundPost() throws Exception {
		PostEdit editPost = PostEdit.builder()
				.title("수정 제목")
				.content("수정 내용")
				.build();

		mockMvc.perform(patch("/posts/{postId}", 100L)    // PATCH /posts/{postId}
						.contentType(APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(editPost)))
				.andExpect(status().isNotFound())
				.andDo(print());
	}

	@Test
	@Transactional
	@DisplayName("글 작성 시 제목에 금지어('바보')는 포함될 수 없다.")
	void banWordsTest() throws Exception {
		PostCreate postCreate = PostCreate.builder()
				.title("바보입니다.")
				.content("내용입니다.")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(postCreate);

		mockMvc.perform(post("/posts")
						.content(json)
						.contentType(APPLICATION_JSON)
				)
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
}
