package com.interest.dao.sf;

import com.interest.model.sf.SfProduct;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SfProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(SfProduct record);

    int insertSelective(SfProduct record);

    SfProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(SfProduct record);

    int updateByPrimaryKey(SfProduct record);
}