package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    void insert(DishDTO dishDTO);

    PageResult querry(DishPageQueryDTO dishPageQueryDTO);

    void deleteBatch(List<Long> ids);

    void update(DishDTO dishDTO);

    void changeStatus(int status, long id);

    DishVO getById(long id);

    List<Dish> list(Long categoryId);

    List<DishVO> listWithFlavor(Dish dish);
}
