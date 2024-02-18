package com.iron.gift.request;

import com.iron.gift.entiry.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PostCreate {

	@NotBlank(message = "제목은 필수입력입니다.")
	public String title;

	@NotBlank(message = "내용은 필수입력입니다.")
	public String content;

	@Builder
	public PostCreate(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public Post toEntity() {
		return Post.builder()
				.title(title)
				.content(content)
				.build();
	}

}
