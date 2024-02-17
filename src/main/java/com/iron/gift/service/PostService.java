package com.iron.gift.service;

import com.iron.gift.entiry.Post;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public void write(PostCreate postCreate) {

		postRepository.save(postCreate.toEntity());

	}


	public Post getPost(Long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
	}
}
