package com.progex.tracker.user.repo;

import com.progex.tracker.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users LIMIT ?2 OFFSET ?1", nativeQuery = true)
    List<User> findAllUsers(int offset, int limit);

}
