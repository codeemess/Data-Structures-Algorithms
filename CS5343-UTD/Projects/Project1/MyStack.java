import java.util.ArrayList;
import java.util.List;
/*
--------------------------------------------------EXECUTION INSTRUCTIONS----------------------------------------------------
To Run the code and see the output on the terminal/command-prompt type
1. javac MyLinkedList.java
2. java NestedSymbolAlgorithm
---------------------------------------------END OF EXECUTION INSTRUCTIONS----------------------------------------------*/

/**
 * Implements My Stack using ArrayList
 */

public class MyStack<AnyType>{
    private List<AnyType> list;

    /*
        initializes new Arraylist
    */
    public MyStack(){
        list = new ArrayList<AnyType>();
    }
    
    /*
        adds an element to the stack
        returns true
    */
    public boolean push(AnyType val){
        addToStack(val);
        return true;
    }

    /*
        adds an element to the end of the list
        returns true
    */
    private void addToStack(AnyType val){
        list.add(val);
    }

    /*
        pops and returns the element that is popper
    */
    public AnyType pop(){
        return removeFromStack();
    }

    /**
     * 
     * @throws IndexOutOfBounds if stack is empty and tries to pop
     * Removes the element from the end of the list no adjustments to be made
     * @return element that is popped
     */
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

    /**
     * @return true is list is empty 
     */
    public boolean isEmpty(){
        return isListEmpty();
    }

    /**
     * checks the size of the list
     * @return true if empty
     */
    private boolean isListEmpty(){
        if(list.size()==0){
            return true;
        }
        return false;
    }
    
}
/**
 * Implements the Nested Symbol Algorithm to check whether paranthesis are balanced or not
 */
class NestedSymbolAlgorithm {

    /**
     * Pushes any opening paranthesis in the stack, and pops the stack whenever a closing paranthesis is encountered
     * if the popped element is not the matching closing paranthesis, returns false.
     * @param s the input expression that needs to be checked for balance.
     * @return whether the stack is empty or not after the expression is evaluated.
     */
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
    
    /**
     * Demonstration of above code
     */
    public static void main(String[] args) {
        String[] expressions = new String[3];
        expressions[0] = "[({})]";
        expressions[1] = "[[[]]";
        expressions[2] = "({{{}}}})(";

        for(int i=0; i<expressions.length;i++){
            System.out.println("---------Evaluating Expression---------");
            System.out.println(expressions[i]);
            if(areParanthesisBalanced(expressions[i]))
                System.out.println("Expression is Balanced");
            else System.out.println("Expression is Not Balanced");
            System.out.println("-----------------------------------------");
        }
    }
}