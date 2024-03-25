package com.iron.gift.repository.post;

import com.iron.gift.domain.Post;
import com.iron.gift.request.post.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

	List<Post> getList(PostSearch postSearch);
}
