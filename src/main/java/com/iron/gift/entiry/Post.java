package com.iron.gift.entiry;

import com.iron.gift.response.PostResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
}
