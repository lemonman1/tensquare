package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("tensquare-base")
public interface LabelClient {
    @GetMapping("label/{id}")
    Result findByLabelId(@PathVariable("id") String labelId);
}
