package com.lizhihong.basecomment.dao.repository;

import com.lizhihong.basecomment.dao.entity.SysRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleDAO extends JpaRepository<SysRole, Integer> {

    @Query("from SysRole where role=:role")
    public SysRole findByRole(@Param(value = "role") String role);
}