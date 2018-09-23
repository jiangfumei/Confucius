package com.towen.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * 根据角色名称查找角色对象
     * @param name
     * @return
     */
    Role findByName(String name);

    /**
     * 返回状态为1的列表
     */
    @Query("select r from Role r where r.status= 1")
    Page<Role> findRoles(Pageable pageable);

    /**
     * 角色名称字段的唯一性验证
     */
    @Query(value = "select count(1) as num from Role where name = ?1 and status = ?2")
    public int findNumByName(@Param("name")String name, @Param("status")String status);

    /**
     *
     * 用户与角色关联
     */
    @Query(value = "select r from Role r where r.id = ?1")
    List<Role> findByRoleId(long id);

    /**
     * 根据id查询角色
     */
    Role findById(long id);

}
