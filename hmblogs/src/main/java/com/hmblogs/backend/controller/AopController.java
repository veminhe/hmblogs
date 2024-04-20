package com.hmblogs.backend.controller;

import com.hmblogs.backend.util.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AopController {

    @PreAuthorize(hasPermi = "system:stock:list")
    /**
     * findAllStocks
     * @return
     */
    @GetMapping(value = "/findAllStocks2")
    public String findAllStocks(){
        return "stockTest";
    }

}
