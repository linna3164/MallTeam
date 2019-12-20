package com.xmu.discount.mapper;

import com.xmu.discount.domain.discount.GrouponRule;
import com.xmu.discount.domain.discount.GrouponRulePo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  ln
 * @Description 团购活动相关的mapper接口
 * @ate 2019/12/10
 */
@Component
@Mapper
public interface GrouponRuleMapper {

    /**
     * 用id找团购规则
     * @param id
     * @return
     */
    GrouponRulePo getGrouponRuleById(Integer id);


    /**
     * 添加团购规则
     * @param grouponRule
     * @return
     */
    boolean addGrouponRule(GrouponRulePo grouponRule);

    /**
     * 查询一个商品的团购规则
     * @param goodsId
     * @return
     */
    List<GrouponRulePo> listGrouponRuleByGoodsId(Integer goodsId);

    /**
     * 获得所有团购规则
     * @return
     */
    List<GrouponRulePo> getGrouponRules();

    /**
     * 修改团购规则
     * @return
     */
    boolean updateGrouponRuleById(GrouponRulePo grouponRule);

}
