import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

public class CollectionExample {
    public static void main(final String[] args) {
        Collection<Integer> obj = new ArrayList <>();
        obj.add(1);
        obj.add(2);
        obj.add(3);

        Iterator it = obj.iterator();

        while(it.hasNext()){
        System.out.println(it.next());
        }
    }
}
