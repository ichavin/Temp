package com.chavin.mapper.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chavin.po.Order;
import com.chavin.po.User;
import com.chavin.service.OrderService;
import com.chavin.service.SchemeMeterialService;
import com.chavin.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring-core.xml"})
public class MapperTest {
	
	@Resource
	private UserService userService;
	
	@Resource
	private SchemeMeterialService schemeMeterialService;
	
	@Resource
	private OrderService orderService;
	
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
	
	@Test
	public void test2(){
		Order order = new Order();
		order.setIsTradeSuccess(1);
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idParamter", 1);
			orderService.updateByEntity(map, order);
//			System.out.println(entity.getCustom().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
