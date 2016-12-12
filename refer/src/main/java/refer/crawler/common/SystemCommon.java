package refer.crawler.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 *
 * @author rzy 2016/12/08
 *
 */
public class SystemCommon {
    public static String User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2859.0 Safari/537.36";
    public static String currentCookie;


    public static void sleepRandom5s(int s){
        Random random = new Random();
        try {
            Thread.sleep((s+random.nextInt(5))*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void sleeps(int s){
        Random random = new Random();
        try {
            Thread.sleep((s+random.nextInt(5))*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void printLog(String s){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.print(dateFormat.format(date)+" : ");
        System.out.println(s);
    }
}
