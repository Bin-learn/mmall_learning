package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by  bin
 * Time： 2017/7/26.
 */
public class BigDecimalTest {

    @Test
    public void test1(){
        System.out.println(0.005+0.001);
        System.out.println(1.0-0.02);
        System.out.println(1.23/100);
        System.out.println(4.05*100);

    }
    @Test
    public void test2(){
        BigDecimal b1 = new BigDecimal(0.5);
        BigDecimal b2 = new BigDecimal(0.2);
        System.out.println(b1.add(b2));
    }

    @Test

    //商业中要使用这种String构造器
    public void test3(){
        BigDecimal b1 = new BigDecimal("0.5");
        BigDecimal b2 = new BigDecimal("0.2");
        System.out.println(b1.add(b2));
    }

}
