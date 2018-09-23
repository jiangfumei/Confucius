package com.towen.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名查找数据库，判断此名字的用户是否存在
     * @param username
     * @return
     */
    User findByUsername(String username);
    Page<User> findUsersByusernameNotNull(Pageable pageable);
    User findOneById(long id);
    @Query(value = "select count(1) as num from User where username = ?1 and status = ?2")
    public int findNumByUsername(@Param("username")String username,@Param("status")String status);

    @Query("select u from User u where u.status= 1")
    Page<User> findUsers(Pageable pageable);


}
