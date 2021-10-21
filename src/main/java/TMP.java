import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TMP {
//    public static void main(String[] args) {
//        for (int index = 1; index < 6; index++) {
//            String str = String.valueOf(index).repeat(index);
//            System.out.println(str);
//        }
//    }
    public static void main(String[] args) throws InterruptedException {
        Date date = new Date();
        var currentTime = date.getTime();
        System.out.println(currentTime);
        Thread.sleep(2500);
        Date date2 = new Date();
        var currentTime2 = date2.getTime();
        System.out.println(currentTime2);
        System.out.println(TimeUnit.MILLISECONDS.toDays(currentTime2 - currentTime));
    }
}