package com.xmu.discount.mapper;

import com.xmu.discount.domain.discount.PresaleRuleDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface PresaleRuleMapper {


    /**
     * 用id找预售规则
     * @param id
     * @return
     */
    PresaleRuleDto getPresaleRuleById(Integer id);

    /**
     * 添加预售规则
     * @param id
     * @return
     */
    int addPresaleRuleDto(PresaleRuleDto presaleRuleDto);

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    List<PresaleRuleDto> listPresaleRuleByGoodsId(Integer goodsId);

    /**
     * 修改预售规则
     * @return
     */
    int updatePresaleRuleById(PresaleRuleDto presaleRuleDto);
}
