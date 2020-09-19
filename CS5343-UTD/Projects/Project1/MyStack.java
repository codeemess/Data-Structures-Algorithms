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
class NestedSymbolAlgorithm {

    static boolean areParanthesisBalanced(String s){
        MyStack<Character> stack = new MyStack<>();
        for(int i=0; i<s.length(); i++){
            char currentChar = s.charAt(i);
            if(currentChar == '(' || currentChar == '[' || currentChar == '{'){
                stack.push(currentChar);
                continue;
            }
            else if(currentChar == ')'|| currentChar == '}' || currentChar == ']'){
                if (stack.isEmpty()){
                    return false;
                }
                char element = stack.pop();
                switch(currentChar){
                    
                    case ')':
                    if(element == '{' || element == '[')
                        return false;
                    break;
                    
                    case ']':
                    if(element == '{' || element == '(')
                        return false;
                    break;
                    
                    case '}':
                    if(element == '[' || element == '(')
                        return false;
                    break;
                    
                }  
            }
        }
        return stack.isEmpty();
    }
    public static void main(String[] args) {
        String[] expressions = new String[3];
        expressions[0] = "[({})]";
        expressions[1] = "[[[]]";
        expressions[2] = "({{{}}}})(";

        for(int i=0; i<expressions.length;i++){
            if(areParanthesisBalanced(expressions[i]))
                System.out.println("Expression is Balanced");
            else System.out.println("Expression is Not Balanced");
        }
    }
}