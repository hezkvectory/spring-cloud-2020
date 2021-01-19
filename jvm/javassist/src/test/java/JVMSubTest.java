import java.util.concurrent.TimeUnit;

public class JVMSubTest {
    public static void main(String[] args) {
        int a = 3;
        int b = 2;
        int c = a - b;
        System.out.println(c);

        StaticClass staticClass = new StaticClass();
        staticClass.init();
    }

    public void init3(){
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
    }
}
