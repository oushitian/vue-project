package com.interest.dao.sf;

import com.interest.model.sf.SfProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SfProductMapper {
    int deleteByPrimaryKey(Integer productId);

    int insert(SfProduct record);

    int insertSelective(SfProduct record);

    SfProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(SfProduct record);

    int updateByPrimaryKey(SfProduct record);

    List<SfProduct> findAll(@Param("number") String number);

    Integer countTotal();
}