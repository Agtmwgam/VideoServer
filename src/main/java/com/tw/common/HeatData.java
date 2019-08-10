package com.tw.common;

import com.tw.controller.DeviceVideoController;
import com.tw.entity.Point;
import com.tw.entity.WarningMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lushiqin
 * @Description:
 * @Date: 2019/8/8
 * @param: null
 * @return:
 */
@Component
public class HeatData {

    private static Logger log = Logger.getLogger(HeatData.class);

    public  List<Point> caculateHeatData(List<WarningMessage> list) {

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
        for (WarningMessage message : list) {
            String targetLocation = message.getTargetLocation();
            String[] location = targetLocation.split(",");
            //解析目标位置，每条message信息里面包括四个值message.x, message.y, message.width, message.height
            //x代表目标出现的横坐标，x代表目标出现的纵坐标，width，height分别为目标矩形的高和宽
            int x = Integer.parseInt(location[0]);
            int y = Integer.parseInt(location[1]);
            int width = Integer.parseInt(location[2]);
            int height = Integer.parseInt(location[3]);

            for (i = x; i < x + width; i = i + 1) {
                for (j = y; j < y + height; j = j + 1) {
                    if (i <= ROW - 1 && j < COL - 1) {
                        data[i][j] = data[i][j] + 1;
                       /* System.out.println("points.push({x:"+i+",y:"+j+",value:"+data[i][j]+"});");
                        Point p=new Point(i,j,data[i][j]);//将x,y,权重值存入点对象中
                        pointlist.add(p);*/
                    }
                }
            }
        }


        //获取数组里的最大值，进行归一化，最终结果：data最大值为255，最小值为0
        int maxvalue = data[0][0];
        for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                if (maxvalue < data[i][j]) {
                    maxvalue = data[i][j];
                }
            }
        }

        for (i = 0; i < ROW; i = i + 1) {
            for (j = 0; j < COL; j = j + 1) {
                if (maxvalue == 0) {
                    data[i][j] = 0;
                } else {
                    data[i][j] = Math.round(data[i][j] / maxvalue * 255); //round取整数，或者用mod函数取余数

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
                if(data2[i][j] !=0){
                    log.info("points.push({x:" + i + ",y:" + j + ",value:" + data2[i][j] + "});");
                }
            }
        }


        for (i=FSIZE; i<ROW-FSIZE; i=i+1)
        {
            for (j=FSIZE; j<COL-FSIZE; j=j+1)
            {
                data[i][j] = data2[i][j]; //将平滑滤波结果返回给data
                Point p = new Point(i, j, data2[i][j]);//将x,y,权重值存入点对象中
                pointlist.add(p);
            }
        }


        return pointlist;
    }


    public static void main (String[] args ){
        List<WarningMessage> list =new ArrayList<WarningMessage>();

        /*WarningMessage w11=new WarningMessage();w11.setTargetLocation("12,52,20,20");list.add(w11);
        WarningMessage w12=new WarningMessage();w12.setTargetLocation("12,50,18,20");list.add(w12);
        WarningMessage w13=new WarningMessage();w13.setTargetLocation("12,50,20,18");list.add(w13);
        WarningMessage w14=new WarningMessage();w14.setTargetLocation("10,52,20,20");list.add(w14);
        WarningMessage w15=new WarningMessage();w15.setTargetLocation("10,50,16,18");list.add(w15);
        WarningMessage w16=new WarningMessage();w16.setTargetLocation("50,52,20,30");list.add(w16);
        WarningMessage w17=new WarningMessage();w17.setTargetLocation("52,50,30,20");list.add(w17);
        WarningMessage w18=new WarningMessage();w18.setTargetLocation("50,52,30,30");list.add(w18);
        WarningMessage w19=new WarningMessage();w19.setTargetLocation("52,50,20,20");list.add(w19);
        WarningMessage w20=new WarningMessage();w20.setTargetLocation("50,52,20,30");list.add(w20);
        WarningMessage w21=new WarningMessage();w21.setTargetLocation("120,62,10,30");list.add(w21);
        WarningMessage w22=new WarningMessage();w22.setTargetLocation("130,60,20,10");list.add(w22);
        WarningMessage w23=new WarningMessage();w23.setTargetLocation("140,62,10,30");list.add(w23);
        WarningMessage w24=new WarningMessage();w24.setTargetLocation("100,60,20,30");list.add(w24);
        WarningMessage w25=new WarningMessage();w25.setTargetLocation("120,62,20,10");list.add(w25);
        WarningMessage w26=new WarningMessage();w26.setTargetLocation("125,60,16,18");list.add(w26);
        WarningMessage w27=new WarningMessage();w27.setTargetLocation("430,47,10,10");list.add(w27);
        WarningMessage w28=new WarningMessage();w28.setTargetLocation("440,49,10,12");list.add(w28);
        WarningMessage w29=new WarningMessage();w29.setTargetLocation("450,45,12,10");list.add(w29);
        WarningMessage w30=new WarningMessage();w30.setTargetLocation("432,44,14,10");list.add(w30);
        WarningMessage w31=new WarningMessage();w31.setTargetLocation("400,45,10,16");list.add(w31);
        WarningMessage w32=new WarningMessage();w32.setTargetLocation("430,47,13,10");list.add(w32);
        WarningMessage w33=new WarningMessage();w33.setTargetLocation("823,54,8,12");list.add(w33);
        WarningMessage w34=new WarningMessage();w34.setTargetLocation("825,56,9,12");list.add(w34);
        WarningMessage w35=new WarningMessage();w35.setTargetLocation("833,57,18,12");list.add(w35);
        WarningMessage w36=new WarningMessage();w36.setTargetLocation("853,50,8,22");list.add(w36);
        WarningMessage w37=new WarningMessage();w37.setTargetLocation("873,40,28,12");list.add(w37);
        WarningMessage w38=new WarningMessage();w38.setTargetLocation("833,43,18,12");list.add(w38);
        WarningMessage w39=new WarningMessage();w39.setTargetLocation("1030,34,40,30");list.add(w39);
        WarningMessage w40=new WarningMessage();w40.setTargetLocation("1040,36,30,20");list.add(w40);*/
        WarningMessage w41=new WarningMessage();w41.setTargetLocation("1050,32,30,20");list.add(w41);
        WarningMessage w42=new WarningMessage();w42.setTargetLocation("1050,30,20,20");list.add(w42);
        WarningMessage w43=new WarningMessage();w43.setTargetLocation("1034,544,10,20");list.add(w43);
        WarningMessage w44=new WarningMessage();w44.setTargetLocation("1008,554,20,10");list.add(w44);
        WarningMessage w45=new WarningMessage();w45.setTargetLocation("1058,524,10,10");list.add(w45);
        WarningMessage w46=new WarningMessage();w46.setTargetLocation("1028,534,10,10");list.add(w46);
        WarningMessage w47=new WarningMessage();w47.setTargetLocation("1068,554,20,10");list.add(w47);
        WarningMessage w48=new WarningMessage();w48.setTargetLocation("123,554,48,32");list.add(w48);
        WarningMessage w49=new WarningMessage();w49.setTargetLocation("123,554,48,32");list.add(w49);
        WarningMessage w50=new WarningMessage();w50.setTargetLocation("1023,554,28,32");list.add(w50);

        HeatData heatData=new HeatData();


        heatData.caculateHeatData(list);


    }


}
