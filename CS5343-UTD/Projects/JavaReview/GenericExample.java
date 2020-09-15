class Container<AnyType>{
    private AnyType value;

    public AnyType getValue() {
        return value;
    }

    public void setValue(AnyType value) {
        this.value = value;
    }
}

public class GenericExample {
    public static void main(String[] args) {
        System.out.println("Hi from Generics");
        Container<Double> i = new Container<>();
        i.setValue(10.10);
        double x = i.getValue();
        System.out.println(i.getValue() + x);
    }
}
