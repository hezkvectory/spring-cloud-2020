namespace java com.hezk.thrift

enum Operation {
  ADD = 1,
  SUBTRACT = 2,
  MULTIPLY = 3,
  DIVIDE = 4
}

struct Work {
  1: i32 num1 = 0,
  2: i32 num2,
  3: Operation op,
  4: optional string comment,
}

service Calculator {
   i32 add(1:i32 num1, 2:i32 num2),
   i32 addList(1:list<Work> workList),
}

service Sender {
       void ping(),
}

service  HelloWorldService {
  string sayHello(1:string username)
}
