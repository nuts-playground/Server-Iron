package com.iron.gift.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iron.gift.entiry.Post;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostRepository postRepository;

	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("/hello 요청 시 Hello World를 출력한다.")
	void helloWorldTest() throws Exception {

		mockMvc.perform(get("/hello")
						.accept(MediaType.APPLICATION_JSON)
						.param("param", "World"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World"));
	}

	@Test
	@DisplayName("/posts 요청 시 title 값은 필수다.")
	void titleNotNullTest() throws Exception {
		PostCreate postCreate = PostCreate.builder()
				.content("내용입니다.")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(postCreate);

		mockMvc.perform(post("/posts")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.code").value("400"))
				.andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
				.andExpect(jsonPath("$.validation.title").value("제목은 필수입력입니다."))
				.andDo(print());
	}

	@Test
	@DisplayName("/posts 요청 시 DB에 값이 저장된다.")
	void writePostTest() throws Exception {
		PostCreate postCreate = PostCreate.builder()
				.title("제목입니다.")
				.content("내용입니다.")
				.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(postCreate);

		mockMvc.perform(post("/posts")
						.content(json)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andDo(print());

		assertEquals(1L, postRepository.count());

		Post post = postRepository.findAll().get(0);
		assertEquals("제목입니다.", post.getTitle());
		assertEquals("내용입니다.", post.getContent());
	}

	@Test
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

		mockMvc.perform(get("/posts?page=0&sort=id,desc")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", Matchers.is(5)))
				.andExpect(jsonPath("$[0].id").value("30"))
				.andExpect(jsonPath("$[0].title").value("제목 - 30"))
				.andExpect(jsonPath("$[0].content").value("내용 - 30"))
				.andDo(print());

	}
}
