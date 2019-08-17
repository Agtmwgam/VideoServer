package com.tw.service;

import com.tw.entity.Point;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lushiqin
 * @Description: 密度图片生成服务类
 * @Date: 2019/8/8
 * @return:
 */
@Service
public class HeatDataService {

    private static Logger log = Logger.getLogger(HeatDataService.class);

    public  List<Point> caculateHeatData(List<String> list) {

        List<Point> pointlist = new ArrayList<Point>();//返回值为一个三位数组
        int ROW = 1280;//图片尺寸
        int COL = 720;//图片尺寸
        int FSIZE = 10;//平滑滤波尺寸

        int[][] picture = new int[ROW][COL]; //原始保存的图片，需要定时从嵌入式终端读取，灰度图片
        int[][] data = new int[ROW][COL]; //密度数据图片

        int i = 0, j = 0, k = 0;
        int ii = 0, jj = 0;

        //先初始化data全为0
        for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                data[i][j] = 0;
            }
        }

        //把报警信息的数据读到data里面来
        for (String targetLocation : list) {
            String[] location = targetLocation.split(",");
            //解析目标位置，每条message信息里面包括四个值message.x, message.y, message.width, message.height
            //x代表目标出现的横坐标，x代表目标出现的纵坐标，width，height分别为目标矩形的高和宽
            int x = Integer.parseInt(location[0]);  //12,52,20,20
            int y = Integer.parseInt(location[1]);
            int width = Integer.parseInt(location[2]);
            int height = Integer.parseInt(location[3]);

            for (i = x; i < x + width; i = i + 1) {
                for (j = y; j < y + height; j = j + 1) {
                    data[i][j] = data[i][j] + 1;
                }
            }
        }


        /*for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                if(data[i][j]!=0) {
                    System.out.println(i + "\t" + j + "\t" + data[i][j]);
                }
            }
        }*/

        //获取数组里的最大值，进行归一化，最终结果：data最大值为255，最小值为0
        int maxvalue = data[0][0];
        for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                if (maxvalue < data[i][j]) {
                    maxvalue = data[i][j];
                }
            }
        }

        //如果最大值为0，则返回
        if (maxvalue == 0) {
            return  null;
        }

        System.out.println("COL"+COL);
        for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                data[i][j] = Math.round(data[i][j] / maxvalue * 255); //round取整数，或者用mod函数取余数

                if(data[i][j] !=0){
                   // log.info("points.push({x:" + i + ",y:" + j + ",value:" + data[i][j] + "});");

                   System.out.println("x:" + i + ",y:" + j + ",value:" + data[i][j] + "");
                }
            }
        }

        // 平滑滤波，避免数据显示太突兀
        int[][] data2 = new int[ROW][COL];  //密度数据图片2
        int sumdata = 0;
        for (i = FSIZE; i < ROW - FSIZE; i = i + 1) {
            for (j = FSIZE; j < COL - FSIZE; j = j + 1) {
                for (ii = i - FSIZE; ii < i + FSIZE; ii = ii + 1) {
                    for (jj = j - FSIZE; jj < j + FSIZE; jj = jj + 1) {
                        sumdata = sumdata + data[ii][jj]; //获得平滑块的总和，方便后面取均值
                    }
                }
                data2[i][j] = sumdata / ((FSIZE + 1) * (FSIZE + 1)); //获得整个平滑块的均值
                sumdata = 0;

            }
        }


        for (i=FSIZE; i<ROW-FSIZE; i=i+1)
        {
            for (j=FSIZE; j<COL-FSIZE; j=j+1)
            {
                data[i][j] = data2[i][j]; //将平滑滤波结果返回给data

            }
        }

        for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                if(data[i][j]!=0) {
                   // System.out.println(i + "\t" + j + "\t" + data[i][j]);
                }
            }
        }


        return pointlist;
    }


    public static void main (String[] args ){
        List<String> list =new ArrayList<String>();

        list.add("12,52,20,20");
        list.add("12,50,18,20");
        list.add("12,50,20,18");
        list.add("10,52,20,20");
        list.add("10,50,16,18");
        list.add("50,52,20,30");
        list.add("52,50,30,20");
        list.add("50,52,30,30");
        list.add("52,50,20,20");
        list.add("50,52,20,30");
        list.add("120,62,10,30");
        list.add("130,60,20,10");
        list.add("140,62,10,30");
        list.add("100,60,20,30");
        list.add("120,62,20,10");
        list.add("125,60,16,18");
        list.add("430,47,10,10");
        list.add("440,49,10,12");
        list.add("450,45,12,10");
        list.add("432,44,14,10");
        list.add("400,45,10,16");
        list.add("430,47,13,10");
        list.add("823,54,8,12");
        list.add("825,56,9,12");
        list.add("833,57,18,12");
        list.add("853,50,8,22");
        list.add("873,40,28,12");
        list.add("833,43,18,12");
        list.add("1030,34,40,30");
        list.add("1040,36,30,20");
        list.add("1050,32,30,20");
        list.add("1050,30,20,20");
        list.add("1034,544,10,20");
        list.add("1008,554,20,10");
        list.add("1058,524,10,10");
        list.add("1028,534,10,10");
        list.add("1068,554,20,10");
        list.add("123,554,48,32");
        list.add("123,554,48,32");
        list.add("1023,554,28,32");

        HeatDataService heatDataService =new HeatDataService();

        heatDataService.caculateHeatData(list);


    }


}
