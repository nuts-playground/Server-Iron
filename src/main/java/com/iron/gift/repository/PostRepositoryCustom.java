package com.iron.gift.repository;

import com.iron.gift.entity.Post;
import com.iron.gift.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

	List<Post> getList(PostSearch postSearch);
}
