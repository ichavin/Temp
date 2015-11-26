package com.chavin.mapper.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chavin.po.User;
import com.chavin.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-core.xml"})
public class MapperTest {
	
	@Resource
	private UserService userService;
	
	@Test
	public void test1(){
		try {
			//User user = userService.findById(1);
			//System.out.println(user.toString());
			//PageHelper.startPage(1, 10);
			//PageHelper.orderBy("id desc");
			User entity = new User();
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(1);
			ids.add(2);
			entity.setIds(ids);
			List<User> users = userService.findListByEntity(entity);
			System.out.println(users.toString());
			//List<User> users = userService.findListByEntity(entity);
			//DataPageInfo<User> dataPageInfo = new DataPageInfo<User>(users);
			//JSONObject jsonObject = JSONObject.fromObject(dataPageInfo);
			//System.out.println(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
