package com.shoplegacy.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface OrderSequenceMapper {
    Integer selectForUpdate(@Param("seqDate") Date seqDate);
    void insertSequence(@Param("seqDate") Date seqDate, @Param("seqNo") int seqNo);
    void updateSequence(@Param("seqDate") Date seqDate);
}
