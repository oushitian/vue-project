package com.interest.dao.sf;

import com.interest.model.sf.SfRegion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SfRegionMapper {
    int deleteByPrimaryKey(Integer regionId);

    int insert(SfRegion record);

    int insertSelective(SfRegion record);

    SfRegion selectByPrimaryKey(Integer regionId);

    int updateByPrimaryKeySelective(SfRegion record);

    int updateByPrimaryKey(SfRegion record);
}