package com.sharding.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sharding.demo.mapper.OrderMapper;
import com.sharding.demo.pojo.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String SUCCESS = "SUCCESS";
    private static final String FAIL = "FAIL";

    @Resource
    private OrderMapper orderMapper;

    //订单列表，列出分库分表的数据
    @GetMapping("/orderlist")
    public String list(Model model, @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage) {

        PageHelper.startPage(currentPage, 5);
        List<Order> orderList = orderMapper.selectAllOrder();
        model.addAttribute("orderlist", orderList);
        PageInfo<Order> pageInfo = new PageInfo<>(orderList);
        model.addAttribute("pageInfo", pageInfo);
        System.out.println("------------------------size:" + orderList.size());
        return "order/list";
    }


    //添加一个订单
    @GetMapping("/addorder")
    @ResponseBody
    public String addOrder(@RequestParam(value = "orderid", required = true, defaultValue = "0") Long orderId) throws SQLException, IOException {

        String goodsId = "3";
        String goodsNum = "1";

        String goodsName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

        Order orderOne = new Order();
        orderOne.setOrderId(orderId);
        orderOne.setGoodsName(goodsName);

        int resIns = orderMapper.insertOneOrder(orderOne);
        System.out.println("orderId:" + orderOne.getOrderId());

        if (resIns > 0) {
            return SUCCESS;
        } else {
            return FAIL;
        }
    }

}

