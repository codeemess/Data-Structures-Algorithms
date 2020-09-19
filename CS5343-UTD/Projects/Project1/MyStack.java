import java.util.ArrayList;
import java.util.List;


public class MyStack<AnyType>{
    private List<AnyType> list;

    public MyStack(){
        list = new ArrayList<AnyType>();
    }
    
    public void push(AnyType val){
        addToStack(val);
    }

    private void addToStack(AnyType val){
        list.add(val);
    }

    public AnyType pop(){
        return removeFromStack();
    }

    private AnyType removeFromStack(){
        if(list.size() == 0){
            throw new IndexOutOfBoundsException( "Stack is Empty");
        }
        else{
            AnyType element = list.get(list.size() - 1);
            list.remove(list.size()-1); 
            return element;
        }
    }

    public boolean isEmpty(){
        return isListEmpty();
    }

    private boolean isListEmpty(){
        if(list.size()==0){
            return true;
        }
        return false;
    }

    public void reset(){
        flush();
    }

    private void flush(){
        list = new ArrayList<>();
    }
    
}