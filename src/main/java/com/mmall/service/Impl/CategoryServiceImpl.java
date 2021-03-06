package com.mmall.service.Impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.CategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by  bin
 * Time： 2017/7/22.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryMapper categoryMapper;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public ServerResponse addCategory(String categoryName, Integer parentId){
        if(parentId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("品类参数有误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        int resultCount = categoryMapper.insert(category);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategory(String categoryName, Integer categoryId){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMessage("品类参数有误");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setId(categoryId);
        int rowResult = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowResult>0){
            return ServerResponse.createBySuccessMessage("更新品类名字成功");
        }
        return ServerResponse.createByErrorMessage("更新品类名字失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId){
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(parentId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Integer>  categoryIdList = Lists.newArrayList();
        if(categoryId!=null){
            for(Category categoryItem:categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }


    //递归算法，算出子节点
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId ){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category !=null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem:categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return  categorySet;
    }
}
