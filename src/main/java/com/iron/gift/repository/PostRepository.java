package com.iron.gift.repository;

import com.iron.gift.entiry.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {


}
