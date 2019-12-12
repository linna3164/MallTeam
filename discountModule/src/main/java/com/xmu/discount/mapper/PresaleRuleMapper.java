package com.xmu.discount.mapper;

import com.xmu.discount.domain.discount.PresaleRule;
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
    PresaleRule getPresaleRuleById(Integer id);

    /**
     * 添加预售规则
     * @param presaleRuleDto
     * @return
     */
    int addPresaleRule(PresaleRule presaleRule);

    /**
     * 查询一个商品的预售规则
     * @param goodsId
     * @return
     */
    List<PresaleRule> listPresaleRuleByGoodsId(Integer goodsId);

    /**
     * 修改预售规则
     * @return
     */
    int updatePresaleRuleById(PresaleRule presaleRule);
}
