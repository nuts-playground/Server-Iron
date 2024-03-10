package com.iron.gift.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostEdit {

	@NotBlank(message = "제목은 필수입력입니다.")
	public String title;

	@NotBlank(message = "내용은 필수입력입니다.")
	public String content;

	public PostEdit() {
	}

	@Builder
	public PostEdit(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
