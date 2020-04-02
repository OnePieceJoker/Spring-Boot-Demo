package com.lizhihong.basecomment.controller;

import java.util.HashMap;

import com.lizhihong.basecomment.api.vo.R;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class ReturnTestController {

    @RequestMapping("/getSuccess")
    public R demo() {
        return R.error().data("data", new HashMap<String, Object>());
    }
}