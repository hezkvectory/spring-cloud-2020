import com.hezk.zk.common.ExpiryQueue;
import com.hezk.zk.common.Time;
import org.junit.Test;

import java.nio.channels.SelectionKey;
import java.util.concurrent.TimeUnit;

public class SelectionKeyTest {

    int expirationInterval = 10000;

    @Test
    public void test1(){
        System.out.println(1 << 5);
    }

    @Test
    public void test() {
        System.out.println(SelectionKey.OP_READ);
    }

    @Test
    public void expire() throws InterruptedException {
        System.out.println(System.nanoTime());
        long time = Time.currentElapsedTime();
        System.out.println(time);

        for (int i = 0; i < 20; i++) {
            System.out.println(roundToNextInterval(Time.currentElapsedTime()));
            TimeUnit.MILLISECONDS.sleep(2000);
        }
    }

    private long roundToNextInterval(long time) {
        return (time / expirationInterval + 1) * expirationInterval;
    }

}
