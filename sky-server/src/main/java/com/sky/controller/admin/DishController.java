package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品类接口")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result insert(@RequestBody DishDTO dishDTO) {
        //构造key
        String key = "dish_" + dishDTO.getCategoryId();
        //清理缓存
        redisTemplate.delete(key);
        dishService.insert(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> pageQuerry(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询条件为{}", dishPageQueryDTO);
        PageResult pageResult = dishService.querry(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除")
    public Result delete(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        //将所有缓存清楚
        cleanCache("dish_*");
        return Result.success();
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
        dishService.update(dishDTO);
        cleanCache("dish_*");
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result changeStatus(@PathVariable int status, long id) {
        dishService.changeStatus(status, id);
        cleanCache("dish_*");
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取菜品")
    public Result<DishVO> getById(@PathVariable long id) {
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }


    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    public void cleanCache(String pattern) {
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
