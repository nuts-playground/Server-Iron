package com.iron.gift.service;

import com.iron.gift.domain.Post;
import com.iron.gift.domain.PostEditor;
import com.iron.gift.exception.PostNotFound;
import com.iron.gift.repository.PostRepository;
import com.iron.gift.request.PostCreate;
import com.iron.gift.request.PostEdit;
import com.iron.gift.request.PostSearch;
import com.iron.gift.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
				.orElseThrow(PostNotFound::new);

		return PostResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.build();

	}

	public List<PostResponse> getList(PostSearch postSearch) {
		return postRepository.getList(postSearch).stream()
				.map(post -> post.toResponse())
				.collect(Collectors.toList());
	}

	@Transactional
	public Post editPost(Long id, PostEdit postEdit) {
		Post findPost = postRepository.findById(id)
				.orElseThrow(PostNotFound::new);

		PostEditor.PostEditorBuilder editorBuilder = findPost.toEditor();


		if (postEdit.getTitle() != null) {
			editorBuilder.title(postEdit.getTitle());
		}

		if (postEdit.getContent() != null) {
			editorBuilder.content(postEdit.getContent());
		}

		PostEditor postEditor = editorBuilder.build();

		findPost.edit(postEditor);

		return findPost;
	}

	public void deletePost(Long id) {
		Post findPost = postRepository.findById(id)
				.orElseThrow(PostNotFound::new);

		postRepository.delete(findPost);
	}
}
