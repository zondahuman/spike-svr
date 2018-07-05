package com.abin.lee.spike.conc.test;

import com.abin.lee.spike.common.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by abin on 2018/6/21.
 */
public class JumpPointTimeTest {

    public static void main(String[] args) throws InterruptedException {
        int result = (int) (Math.random() * 2) ;
        System.out.println("result="+result);

        allTime(1000);
    }



    public static void allTime(int threadTime) throws InterruptedException {
        int basic = 1;
        TaskVo taskVo = new TaskVo();
        taskVo.finish = false ;
        while(true){
            if(taskVo.finish == false){
                System.out.println(DateUtil.getYMDHMSTime() + " , threadTime=" + threadTime + "   , basic=" + basic + "   , threadName=" + Thread.currentThread().getId());
                Long sleepTime = algo(threadTime, basic);
                Thread.sleep(sleepTime);
                System.out.println(DateUtil.getYMDHMSTime() + "   , threadTime=" + threadTime  + "   , sleepTime=" + sleepTime + "   , threadName=" + Thread.currentThread().getId());
                boolean flag = hope();
                System.out.println("flag=" + flag + "   , threadTime=" + threadTime + "   , threadName=" + Thread.currentThread().getId());
                if(flag){
                    taskVo.finish = true ;
                }else{
                    basic ++ ;
                }
            }else{
                basic = 1;
                System.out.println(DateUtil.getYMDHMSTime() + "   , basic=" + basic + "   , threadName=" + Thread.currentThread().getId());
                break;
            }

        }


    }

    public static Long algo(int threadTime, int basic){
        Long result = (long)(3000 * Math.pow(2, basic));
        return result;
    }
    public static Boolean hope(){
        Boolean[] boo = new Boolean[]{false, false, false, true, false, false};
        int result = (int) (Math.random() * 6) ;
        return  boo[result];
    }


    @Getter
    @Setter
    public static class TaskVo{
        String taskName;
        boolean finish;
        boolean async;
        boolean wait = true;

    }

}
