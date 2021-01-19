public class JVMSubTest {
    //-javaagent:/path/agent.jar
    public static void main(String[] args) {
//        StaticClass.init();
        int a = 3;
        int b = 2;
        int c = a - b;
        System.out.println(c);
        StaticClass.init(13);
    }

}
