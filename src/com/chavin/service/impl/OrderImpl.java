package com.chavin.service.impl;

import org.springframework.stereotype.Service;

import com.chavin.dao.OrderMapper;
import com.chavin.po.Order;
import com.chavin.service.OrderService;

@Service
public class OrderImpl extends BaseServiceImpl<OrderMapper, Order> implements OrderService {

}
