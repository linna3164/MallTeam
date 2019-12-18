package com.xmu.discount.domain.discount;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xmu.discount.util.JacksonUtil;
import net.sf.json.JSONArray;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hsx
 * @version 1.0
 * @date 2019/12/18 0:16
 */
class GrouponRuleTest {
    @Test
    void getStrategyList()
    {
        String jsonString ="{ \"strategy\": [{\"lowerbound\": 50,\"upper bound\":100,\"rate\":5},{\"lowerbound\": 100,\"upper bound\":200,\"rate\":15}]}";
        String array = "[{\"lowerbound\": 50,\"upper bound\":100,\"rate\":5},{\"lowerbound\": 100,\"upper bound\":200,\"rate\":15}]";
//        JsonParser jp = new JsonParser();
//        JsonObject jo = jp.parse(jsonString).getAsJsonObject();
//        JsonArray messageArray = jo.get("strategy").getAsJsonArray();
//        System.out.println(messageArray);
//        JSONArray jsonArray = JSONArray.fromObject(messageArray.getAsString());
//        JSONArray jsonArray = JSONArray.fromObject(array);
//        System.out.println(jsonArray);
//        List<GrouponRule.Strategy> strategiesString = JSONArray.toList(jsonArray, GrouponRule.Strategy.class);
//        for(GrouponRule.Strategy strategy:strategiesString)
//        {
//            System.out.println(strategy);
//        }

//        jsonString = org.apache.commons.text.StringEscapeUtils.unescapeJson(jsonString);
//        List<GrouponRule.Strategy> strategiesString = JacksonUtilGrouponRule.Strategy.class(jsonString, "strategy",GrouponRule.Strategy.class);
//        List<GrouponRule.Strategy> strategies = new ArrayList<>();
//        for (String string : strategiesString) {
//            System.out.println(string);
//            GrouponRule.Strategy strategy = JSON.parseObject(string, GrouponRule.Strategy.class);
//            strategies.add(strategy);
//        }
//        System.out.println(strategies);

        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(jsonString).getAsJsonObject();
        JsonArray messageArray = jo.get("strategy").getAsJsonArray();
        System.out.println(messageArray.toString());
//        System.out.println(messageArray.getAsString());
//        Object object=JSON.parse(string);
//        System.out.println(object);
//        List<GrouponRuleStrategy> strategies=JSON.parseArray(string,GrouponRuleStrategy.class);
//
//        System.out.println(strategies.get(0).getLowerBound());



    }

}