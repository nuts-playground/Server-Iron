package com.iron.gift.service;

import com.iron.gift.entiry.Post;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import com.iron.gift.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Long write(PostCreate postCreate) {
		Post post = postCreate.toEntity();
		postRepository.save(post);
		return post.getId();
	}


	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

		return PostResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.build();

	}

	public List<PostResponse> getList(Pageable pageable) {
		return postRepository.findAll(pageable).stream()
				.map(post -> post.toResponse())
				.collect(Collectors.toList());
	}

}
