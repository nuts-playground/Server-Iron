package com.iron.gift.repository;

import com.iron.gift.domain.Post;
import com.iron.gift.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

	List<Post> getList(PostSearch postSearch);
}
