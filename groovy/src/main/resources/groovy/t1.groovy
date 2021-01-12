def myClosure() {
    def y = [fName: 'Jen', lName: 'Cruise', sex: 'F'];
    println(y)
    y.put("dd", "dd");
    println(y)

    def list = [10, 11, 12, 13, 14, true, false, "BUNTHER"]

    list.each {
        println it
    }

    def cars = [
            new Car(make: 'Peugeot', model: '508'),
            new Car(make: 'Renault', model: 'Clio')]
    def makes = cars*.make
    println(makes)
}

class Car {
    String make
    String model
}