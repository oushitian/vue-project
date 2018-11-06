package com.interest.dao.sf;

import com.interest.model.sf.SfProductDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SfProductDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfProductDetails record);

    int insertSelective(SfProductDetails record);

    SfProductDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfProductDetails record);

    int updateByPrimaryKey(SfProductDetails record);
}