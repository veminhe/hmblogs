package com.hmblogs.backend.controller;

import com.hmblogs.backend.dao.PageQuery;
import com.hmblogs.backend.dao.PageResult;
import com.hmblogs.backend.entity.Blog;
import com.hmblogs.backend.service.BlogServiceImpl;
import com.hmblogs.backend.util.BlogConstant;
import lombok.extern.slf4j.Slf4j;
import com.hmblogs.backend.mode.observe.DemoPublisher;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogServiceImpl blogServiceImpl;

    @PostMapping("findBlogByPage")
    public BlogResponse findBlogByPage(@RequestBody Blog blog,
                                           @Param("pageNum") Integer pageNum,
                                           @Param("pageSize") Integer pageSize){
        PageQuery<Blog> blogPage = new PageQuery<Blog>();
        blogPage.setCriteria(blog);
        blogPage.setPageNum(pageNum);
        blogPage.setPageSize(pageSize);
        PageResult<Blog> result =  blogServiceImpl.findBlogByPage(blogPage);
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setCode(BlogConstant.SUCCESS_CODE);
        blogResponse.setResult(result);
        return blogResponse;
    }

    @PostMapping("updateBlogById")
    public BlogResponse updateBlogById(@RequestBody Blog blog){
        Integer result =  blogServiceImpl.updateBlogById(blog);
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setCode(BlogConstant.SUCCESS_CODE);
        blogResponse.setResult(result);
        return blogResponse;
    }

    @Autowired
    private DemoPublisher demoPublisher;

    @PostMapping("addBlog")
    public BlogResponse addBlog(@RequestBody Blog blog){
        demoPublisher.publish("你好");
        Integer result =  blogServiceImpl.addBlog(blog);
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setCode(BlogConstant.SUCCESS_CODE);
        blogResponse.setResult(result);
        return blogResponse;
    }
}
