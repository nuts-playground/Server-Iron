package com.iron.gift.domain;

import com.iron.gift.response.PostResponse;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Lob
	private String content;

	@Builder
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public PostResponse toResponse() {
		return PostResponse.builder()
				.id(id)
				.title(title)
				.content(content)
				.build();
	}

	public PostEditor.PostEditorBuilder toEditor() {
		return PostEditor.builder()
				.title(title)
				.content(content);
	}

	public void edit(PostEditor postEditor) {
		this.title = postEditor.getTitle();
		this.content = postEditor.getContent();

	}
}
