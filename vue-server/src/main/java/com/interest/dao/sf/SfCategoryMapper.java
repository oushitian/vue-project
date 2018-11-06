package com.interest.dao.sf;

import com.interest.model.sf.SfCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface SfCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(SfCategory record);

    int insertSelective(SfCategory record);

    SfCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(SfCategory record);

    int updateByPrimaryKey(SfCategory record);
}