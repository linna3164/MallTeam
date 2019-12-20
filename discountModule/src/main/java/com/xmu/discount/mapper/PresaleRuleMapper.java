package com.xmu.discount.mapper;

import com.xmu.discount.domain.discount.PresaleRule;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author  ln
 * @Description 预售活动相关的mapper接口
 * @ate 2019/12/10
 */
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
     * 查看所有预售规则
     * @return
     */
    List<PresaleRule> getPresaleRules();
    /**
     * 添加预售规则
     * @param presaleRule
     * @return
     */
    boolean addPresaleRule(PresaleRule presaleRule);

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
    boolean updatePresaleRuleById(PresaleRule presaleRule);
}
