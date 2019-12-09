package com.xmu.discount.mapper;

import com.xmu.discount.domain.PresaleRuleDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PresaleRuleMapper {


    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    PresaleRuleDto getPresaleRuleById(Integer id);

    /**
     * 添加团购规则
     * @param id
     * @return
     */
    int addPresaleRuleDto(Integer id);

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    List<PresaleRuleDto> listPresaleRuleByGoodsId(Integer goodsId);

    /**
     * 修改团购规则
     * @return
     */
    int updatePresaleRuleById(PresaleRuleDto presaleRuleDto);
}
