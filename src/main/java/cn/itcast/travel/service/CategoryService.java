package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

/**
 * Created by fei on 2019/11/24.
 */
public interface CategoryService {
    public List<Category> findAll();
}
