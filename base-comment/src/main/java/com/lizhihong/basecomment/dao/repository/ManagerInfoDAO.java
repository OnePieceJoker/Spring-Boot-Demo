package com.lizhihong.basecomment.dao.repository;

import com.lizhihong.basecomment.dao.entity.ManagerInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "ManagerInfoDAO")
public interface ManagerInfoDAO extends JpaRepository<ManagerInfo, Integer>, JpaSpecificationExecutor<ManagerInfo> {

    @Query("from ManagerInfo where username=:username")
    public ManagerInfo findByUsername(@Param(value = "username") String username);
}