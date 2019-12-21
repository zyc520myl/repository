package com.example.wsz.shudu_final;

import java.util.ArrayList;
import java.util.Random;
/**
 * Created by wsz on 2019/12/21.
 */
public class ShuduData {

    private int[][] number = new int[9][9];
    private StandShudu standShudu=new StandShudu();
    private Random random=new Random();

    public int[][] generateShuDu(){
        /***  获取随机的模板  **/
        String[][] stand=standShudu.getStand(random.nextInt(10));   //random.nextInt随机生成一个整数 范围为0到10
        /** 带选择数字列表 **/
        ArrayList<Integer> data=new ArrayList<Integer>();   //Integer类型的集合
        data.add(1); data.add(2); data.add(3);
        data.add(4); data.add(5); data.add(6);
        data.add(7); data.add(8); data.add(9);         //指定的元素(e)添加到此列表的尾部
        /***  随机存储排序 **/
        int[] s=new int[9];
        int index=0;
        while (index<9){
            int t = random.nextInt(data.size());
            s[index]=data.get(t);
            data.remove(t);
            index++;
        }
        /**  根据模板按照获取的随机序列进行数据填充 **/
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                number[i][j]=s[getIndex(stand[i][j])-1];
            }
        }
        return number;
    }
    private Integer getIndex(String s){
        switch (s){
            case "a":
                return 1;
            case "b":
                return 2;
            case "c":
                return 3;
            case "d":
                return 4;
            case "e":
                return 5;
            case "f":
                return 6;
            case "g":
                return 7;
            case "h":
                return 8;
            case "i":
                return 9;
            default:
                return 1;
        }
    }
    public String initStr(){
        ShuduData shuduData = new ShuduData();
        int[][] data = shuduData.generateShuDu();
        int[][] arr = new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<7;j++){
                int ranNum = random.nextInt(9);
                arr[i][ranNum]=data[i][ranNum];
            }
        }
        String str = "";
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                str+=arr[i][j];
            }
        }
        return str;
    }
}

