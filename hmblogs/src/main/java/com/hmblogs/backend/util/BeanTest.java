package com.hmblogs.backend.util;

import com.hmblogs.backend.entity.Blog;
import com.hmblogs.backend.service.BlogServiceImpl;
import com.hmblogs.backend.service.StockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
//@Component
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanTest {

    @Test
    public void testBean() {
        // 请求参数
        BlogServiceImpl blogServiceImpl = (BlogServiceImpl) BeanUtils.getBean(BlogServiceImpl.class);
        Blog blog = new Blog();
        blog.setBlogTitle("xxxxxxx");
        blog.setBlogContent("fdsafadsfadsfdasf");
        blogServiceImpl.addBlog(blog);
    }

    @Test
    public void testBean22() {
        // 请求参数
        BlogServiceImpl blogServiceImpl = (BlogServiceImpl) SpringUtils.getBeanByClass(BlogServiceImpl.class);
        Blog blog = new Blog();
        blog.setBlogTitle("222222222");
        blog.setBlogContent("22222sasadsdas");
        blogServiceImpl.addBlog(blog);
    }
}
