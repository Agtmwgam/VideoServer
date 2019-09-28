package com.tw.util;

import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

/**
 * @Classname videoFormatConvertUtil
 * @Description 转换视频格式
 * @Date 2019/9/26 0:23
 * @Created zhuoshouyi & liutianwen
 */
public class VideoFormatConvertUtil {

    private static boolean isStart = true;

    public void frameRecord(String inputFile, String outputFile)
            throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
        // 获取视频源
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
        grabber.setOption("rtsp_transport", "tcp");
        //速度
        grabber.setFrameRate(30);
        //码率
        grabber.setVideoBitrate(3000000);

        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720, 1);

        //编码格式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); //avcodec.AV_CODEC_ID_H264  //AV_CODEC_ID_MPEG4
        recordByFrame(grabber, recorder);
    }

    private static void recordByFrame(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder)
            throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
        try {// 建议在线程中使用该方法
            grabber.start();
            //如果视频格式为hev1，则转换成h.264格式
            if (grabber.getVideoCodec() == avcodec.AV_CODEC_ID_HEVC) {
                //速度、码率经过多次测试，此处需要降低为原来3倍，才可支持正常转换
                recorder.setFrameRate(10);
                recorder.setVideoBitrate(1000000);
                recorder.start();
                //CanvasFrame canvas = new CanvasFrame("摄像头");//新建一个窗口
                //     canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //canvas.setAlwaysOnTop(true);

                long t1 = System.currentTimeMillis();

                Frame frame = null;


                while (isStart && (frame = grabber.grabFrame()) != null) {
                    long t2 = System.currentTimeMillis();
                    if (t2 - t1 > 2 * 60 * 60 * 1000) {
                        break;
                    } else {
                        recorder.record(frame);
                        //TODO your work
                    }
                    //canvas.showImage(grabber.grab());//获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像
                }
            }
        } finally {
            if (grabber != null) {
                grabber.stop();
            }
            if (recorder != null) {
                recorder.stop();
            }
        }
    }
}

