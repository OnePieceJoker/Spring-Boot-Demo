package com.lizhihong.basecomment.service;

import com.lizhihong.basecomment.dao.entity.ManagerInfo;
import com.lizhihong.basecomment.dao.repository.ManagerInfoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mr.Joker
 * @date 2020/03/16
 * @time 22:CURRENT_MINUTE:CURRENT_SECOND
 * @description 
 */
@Service
public class ManagerInfoService {

    @Autowired
    ManagerInfoDAO managerInfoDAO;

    public ManagerInfo findByUsername(String username) {
        return managerInfoDAO.findByUsername(username);
    }
}