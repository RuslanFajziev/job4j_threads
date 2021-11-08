import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

public class TMP {
    public static void main(String[] args) {
//        Date date = new Date();
//        var currentTime = date.getTime();
//        System.out.println(currentTime);
//        Thread.sleep(2500);
//        Date date2 = new Date();
//        var currentTime2 = date2.getTime();
//        System.out.println(currentTime2);
//        System.out.println(TimeUnit.MILLISECONDS.toDays(currentTime2 - currentTime));

//        Map<Integer, Base> map = new HashMap<>();
//        Base base = new Base(1, 0);
//        map.put(base.getId(), base);
//
//        Base user1 = map.get(1);
//        user1.setName("User 1");
//
//        Base user2 = map.get(1);
//        user1.setName("User 2");
//
//        map.put(user1.getId(), user1);
//        map.put(user2.getId(), user2);
//
//        System.out.println(map.get(1));
//
//    }
//}
//
//class Base {
//    private final int id;
//    private final int version;
//    private String name;
//
//    public Base(int id, int version) {
//        this.id = id;
//        this.version = version;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public int getVersion() {
//        return version;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    public String toString() {
//        return "Base{"
//                + "id=" + id
//                + ", version=" + version
//                + ", name='" + name + '\''
//                + '}';
//    }

        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println(Runtime.getRuntime().totalMemory());
    }
}