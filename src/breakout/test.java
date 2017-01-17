package breakout;

import java.util.ArrayList;
import java.util.List;

public class test {
    
    public static void main(String[] args) {
        List<Integer> l = new ArrayList<Integer>();
        for(int i = 0; i < 5; i++) {
            l.add(i);
        }
        System.out.println(l.size());
        l.remove((Integer) 3);
        int size = l.size();
        System.out.println(size);
        for(int i = 0; i < size; i++) {
            System.out.println(l.get(i));
        }
    }
}
