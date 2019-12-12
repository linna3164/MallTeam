package com.xmu.discount.mapper;

import com.xmu.discount.domain.discount.GrouponRule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface GrouponRuleMapper {

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    GrouponRule getGrouponRuleById(Integer id);

    /**
     * 添加团购规则
     * @param grouponRule
     * @return
     */
    int addGrouponRule(GrouponRule grouponRule);

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    List<GrouponRule> listGrouponRuleByGoodsId(Integer goodsId);

    /**
     * 修改团购规则
     * @return
     */
    int updateGrouponRuleById(GrouponRule grouponRule);

}
