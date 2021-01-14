import com.hezk.redis.util.Constants;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {

    private static final int NUM = 1000000;  // 100万用户

    private static final String SET_KEY = "SET:USER:LOGIN:2019082811";
    private static final String PF_KEY = "PF:USER:LOGIN:2019082811";

    @Test
    public void insert() {
        Jedis client = new Jedis(Constants.REDIS_IP);
        for (int i = 0; i < NUM; ++i) {
            System.out.println(i);
            client.sadd(SET_KEY, "USER" + i);
            client.pfadd(PF_KEY, "USER" + i);
        }
    }

}