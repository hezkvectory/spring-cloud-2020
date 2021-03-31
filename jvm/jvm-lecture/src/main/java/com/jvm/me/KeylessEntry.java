package com.jvm.me;

public class KeylessEntry {

    static class Key {
        Integer id;

        Key(Integer id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 2; i++) {
//            Key key = new Key(i);
//            System.out.println(key + ":" + key.hashCode());
//        }

        Dog dog = new Dog("white");
        System.out.println(dog.hashCode());
        Dog dog1 = new Dog("white");
        System.out.println(dog.hashCode());
        System.out.println(dog == dog1);
        System.out.println(dog.equals(dog1));

        dog.color = "gre";
        System.out.println(dog.hashCode());


//        Map m = new HashMap();
//        while (true) {
//            for (int i = 0; i < 10000; i++) {
//                if (!m.containsKey(new Key(i))) {
//                    m.put(new Key(i), "Number:" + i);
//                }
//            }
//            System.out.println("m.size()=" + m.size());
//        }
    }

    static class Dog {
        String color;

        public Dog(String s) {
            color = s;
        }

        //重写equals方法, 最佳实践就是如下这种判断顺序:
        public boolean equals(Object obj) {
            if (!(obj instanceof Dog))
                return false;
            if (obj == this)
                return true;
            return this.color == ((Dog) obj).color;
        }

        public int hashCode() {
            return color.length();//简单原则
        }
    }
}