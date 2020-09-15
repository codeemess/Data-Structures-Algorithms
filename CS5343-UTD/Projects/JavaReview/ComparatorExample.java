import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
import java.util.Comparator;


public class ComparatorExample {
    public static void main(String[] args) {
        List<Integer> i = new ArrayList<>();
        i.add(100);
        i.add(201);
        i.add(402);
        i.add(703);
        i.add(604);
        
        // sort the above in ascending order of their last digit

        Comparator c = new Comparator<Integer>(){
            public int compare(Integer i, Integer j){
                if(i%10 > j%10){
                    return 1;
                }else return -1;
            }
        };

        Collections.sort(i, c);

        for(Integer x: i){
            System.out.println(x);
        }
    }
}
