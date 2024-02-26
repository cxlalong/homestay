package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void insert(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();

    void clean();

    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
