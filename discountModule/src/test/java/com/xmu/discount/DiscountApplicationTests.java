package com.xmu.discount;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiscountApplicationTests {

    @Test
    void contextLoads() {
        {"strategy":[{"lowerbound":"0", "upperbound":"10", "rate":"0.88"},
            {"lowerbound":"11", "upperbound":"xxx", "rate":"xxx"}]}
    }

}
