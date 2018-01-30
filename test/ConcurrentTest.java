import com.jwh.demo.StartServer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConcurrentTest {

    private static Lock lock = new ReentrantLock();

    private RedisTemplate<String,String> redisTemplate;

    static class Outer{
        public  void print(String str){
//        lock.lock();
//            synchronized (this){
                for(int i=0;i<str.length();i++){
                    System.out.print(str.charAt(i));
                    try{
                        TimeUnit.MILLISECONDS.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println();
            }
//        lock.unlock();
//        }
    }


//    public static void main(String[] args){
//        String str1 = "abcdefghijklmn";
//        String str2 = "opqrstuvwxyz";
//        Outer outer = new Outer();
//        new Thread(()-> outer.print(str1)).start();
//        new Thread(()-> outer.print(str2)).start();
//    }
    @Before
    public void startUp(){
        StartServer.main(new String[]{});
        redisTemplate = StartServer.context.getBean("stringRedisTemplate",RedisTemplate.class);
    }

    @Test
    public void test(){

    }

}
