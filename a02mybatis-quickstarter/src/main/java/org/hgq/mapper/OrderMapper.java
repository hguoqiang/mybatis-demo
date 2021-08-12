package org.hgq.mapper;

import org.hgq.pojo.Order;

import java.util.List;

public interface OrderMapper {
    List<Order> findAll();
}