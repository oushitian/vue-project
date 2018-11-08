package com.interest.dao.sf;

import com.interest.model.sf.SfProductDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SfProductDetailsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SfProductDetails record);

    int insertSelective(SfProductDetails record);

    SfProductDetails selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SfProductDetails record);

    int updateByPrimaryKey(SfProductDetails record);

    @Select("select * from sf_product_details where product_id = #{productId}")
    List<SfProductDetails> getProductDetailsByProductId(Integer productId);
}