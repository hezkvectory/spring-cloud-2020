package com.hezk.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * *
 * * @author Selecter Wane
 * * @see 给定一串数字判断是否存在这三个元素，它们将数字串分为四个子串，其中每个子串的数字之和
 * *   均相同(该3个元素不纳入计算)，要求时间复杂度和空间复杂度均不能超过O(n)
 * *
 */
public class NumberSplit {


    class Func1 {

        Integer total;      //每部分的和

        String numberStr;     //原始字符串

        Integer[] array;     //和值数组

        Map<Integer, Integer> sumMap = new HashMap<Integer, Integer>();  //对应和值的索引

        Integer[] splitIndex = {0, 0, 0}; //三个元素

        Boolean checkStr = true;   //判断是否全部为数字字符


        Func1(String numberStr) {
            this.init(numberStr);
        }

        /**
         * 初始化
         *
         * @param numberStr 目标字符串
         */
        Func1 init(String numberStr) {
            this.numberStr = numberStr;
            array = new Integer[numberStr.length()];
            int sum = 0;
            for (int index = 0; index < numberStr.length(); index++) {
                if (this.numberStr.charAt(index) < 48 || this.numberStr.charAt(index) > 57) {
                    checkStr = false;
                    return this;
                }
                sum += numberStr.charAt(index) - 48;
                array[index] = sum;
                sumMap.put(sum, index);
            }
            return this;
        }

        /**
         * 执行判断处理
         */
        Boolean excute() {
            boolean flag = false;
            //每部分总和不超过总和的1/4
            total = array[this.numberStr.length() - 1] / 4;
            //遍历第一个元素
            for (int index = 0; index < this.numberStr.length(); index++) {
                //第一个元素不可能为第一位
                if (index == 0) {
                    continue;
                }
                if (array[index - 1] > total) {
                    break;
                }
                int _total = array[index - 1];
                //找第二个元素
                int _secondSum = array[index] + _total;
                if (sumMap.get(_secondSum) != null) {
                    int _secondIndex = sumMap.get(_secondSum) + 1;
                    int _threeSum = array[_secondIndex] + _total;
                    //找第三个元素
                    if (sumMap.get(_threeSum) != null) {
                        int _threeIndex = sumMap.get(_threeSum) + 1;
                        int _fourSum = array[_threeIndex] + _total;
                        //确认最后一部分和值
                        if (_fourSum == array[this.numberStr.length() - 1]) {
                            splitIndex[0] = index;
                            splitIndex[1] = _secondIndex;
                            splitIndex[2] = _threeIndex;
                            total = _total;
                            flag = true;
                        }
                    }
                }

            }
            return flag;
        }

        /**
         * 输出结果详情
         */


        void print() {
            if (!this.checkStr) {
                System.out.println("输入字符串中含有非数字字符\n");
                return;
            }
            if (!this.excute()) {
                System.out.println("不存在这样的三个元素\n");
                return;
            }
            System.out.println("原始字符串为：\t" + this.numberStr + "\n"
                    + "三个元素的索引为：\t" + this.splitIndex[0] + "," + this.splitIndex[1] + "," + this.splitIndex[2] + "\n"

                    + "三个元素为：\t" + this.numberStr.charAt(this.splitIndex[0]) + "," + this.numberStr.charAt(this.splitIndex[1]) + "," + this.numberStr.charAt(this.splitIndex[2]) + "\n"
                    + "四部分为：" + "\n"
                    + "\t" + this.numberStr.substring(0, this.splitIndex[0]) + "\n"
                    + "\t" + this.numberStr.substring(this.splitIndex[0] + 1, this.splitIndex[1]) + "\n"
                    + "\t" + this.numberStr.substring(this.splitIndex[1] + 1, this.splitIndex[2]) + "\n"
                    + "\t" + this.numberStr.substring(this.splitIndex[2] + 1) + "\n"
                    + "每部分和值为：\t" + this.total + "\n");
        }


    }


    @Test
    public void testFunc1_1() {
        new Func1("99999999999").print();
    }


    @Test
    public void testFunc1_2() {
        new Func1("999999").print();
    }


    @Test
    public void testFunc1_3() {
        new Func1("111111152564397").print();
    }


    @Test
    public void testFunc1_4() {
        new Func1("3435363").print();
    }


    @Test
    public void testFunc1_5() {
        new Func1("3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333"
                + "9"

                + "3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333"
                + "6"

                + "3333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333"
                + "5"

                + "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"

                + "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111"

                + "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111").
                print();
    }

}