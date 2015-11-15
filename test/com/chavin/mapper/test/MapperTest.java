package com.chavin.mapper.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chavin.dao.UserMapper;
import com.chavin.po.User;
import com.chavin.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-core.xml"})
public class MapperTest {
	
	@Resource
	private UserService userService;
	
	@Test
	public void test1(){
		try {
			
			PageHelper.startPage(1, 10);
			PageHelper.orderBy("id asc");
			List<User> users = userService.findListByEntitys(null);
			PageInfo<User> pageInfo = new PageInfo<User>(users);
			//User user = userService.getByKey(1);
			//System.out.println(user.getRealName());
			System.out.println(users.get(0).getId());
			System.out.println(users.get(1).getId());
			System.out.println(pageInfo.getTotal());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
