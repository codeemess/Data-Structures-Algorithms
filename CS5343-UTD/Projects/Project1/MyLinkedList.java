import java.util.Iterator;
import java.util.List;

/*
--------------------------------------------------EXECUTION INSTRUCTIONS----------------------------------------------------
To Run the code and see the output on the terminal/command-prompt type
1. javac MyLinkedList.java
2. java TestLinkedList

To see the written code for this project, scroll down to find it after Mark Allen Weiss' Code Block or at line no
---------------------------------------------END OF EXECUTION INSTRUCTIONS----------------------------------------------*/


/*-------------------------------------------------MARK ALLEN WEISS' CODE-----------------------------------------------*/

/**
 * LinkedList class implements a doubly-linked list.
 */
public class MyLinkedList<AnyType> implements Iterable<AnyType>
{
    /**
     * Construct an empty LinkedList.
     */
    public MyLinkedList( )
    {
        doClear( );
    }
    
    private void clear( )
    {
        doClear( );
    }
    
    /**
     * Change the size of this collection to zero.
     */
    public void doClear( )
    {
        beginMarker = new Node<>( null, null, null );
        endMarker = new Node<>( null, beginMarker, null );
        beginMarker.next = endMarker;
        
        theSize = 0;
        modCount++;
    }
    
    /**
     * Returns the number of items in this collection.
     * @return the number of items in this collection.
     */
    public int size( )
    {
        return theSize;
    }
    
    public boolean isEmpty( )
    {
        return size( ) == 0;
    }
    
    /**
     * Adds an item to this collection, at the end.
     * @param x any object.
     * @return true.
     */
    public boolean add( AnyType x )
    {
        add( size( ), x );   
        return true;         
    }
    
    /**
     * Adds an item to this collection, at specified position.
     * Items at or after that position are slid one position higher.
     * @param x any object.
     * @param idx position to add at.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */
    public void add( int idx, AnyType x )
    {
        addBefore( getNode( idx, 0, size( ) ), x );
    }
    
    /**
     * Adds an item to this collection, at specified position p.
     * Items at or after that position are slid one position higher.
     * @param p Node to add before.
     * @param x any object.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size(), inclusive.
     */    
    private void addBefore( Node<AnyType> p, AnyType x )
    {
        Node<AnyType> newNode = new Node<>( x, p.prev, p );
        newNode.prev.next = newNode;
        p.prev = newNode;         
        theSize++;
        modCount++;
    }   
    
    
    /**
     * Returns the item at position idx.
     * @param idx the index to search in.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType get( int idx )
    {
        return getNode( idx ).data;
    }
        
    /**
     * Changes the item at position idx.
     * @param idx the index to change.
     * @param newVal the new value.
     * @return the old value.
     * @throws IndexOutOfBoundsException if index is out of range.
     */
    public AnyType set( int idx, AnyType newVal )
    {
        Node<AnyType> p = getNode( idx );
        AnyType oldVal = p.data;
        
        p.data = newVal;   
        return oldVal;
    }

    /**
     * Gets the Node at position idx, which must range from 0 to size( ) - 1.
     * @param idx index to search at.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between 0 and size( ) - 1, inclusive.
     */
    private Node<AnyType> getNode( int idx )
    {
        return getNode( idx, 0, size( ) - 1 );
    }

    /**
     * Gets the Node at position idx, which must range from lower to upper.
     * @param idx index to search at.
     * @param lower lowest valid index.
     * @param upper highest valid index.
     * @return internal node corresponding to idx.
     * @throws IndexOutOfBoundsException if idx is not between lower and upper, inclusive.
     */    
    private Node<AnyType> getNode( int idx, int lower, int upper )
    {
        Node<AnyType> p;
        
        if( idx < lower || idx > upper )
            throw new IndexOutOfBoundsException( "getNode index: " + idx + "; size: " + size( ) );
            
        if( idx < size( ) / 2 )
        {
            p = beginMarker.next;
            for( int i = 0; i < idx; i++ )
                p = p.next;            
        }
        else
        {
            p = endMarker;
            for( int i = size( ); i > idx; i-- )
                p = p.prev;
        } 
        
        return p;
    }
    
    /**
     * Removes an item from this collection.
     * @param idx the index of the object.
     * @return the item was removed from the collection.
     */
    public AnyType remove( int idx )
    {
        return remove( getNode( idx ) );
    }
    
    /**
     * Removes the object contained in Node p.
     * @param p the Node containing the object.
     * @return the item was removed from the collection.
     */
    private AnyType remove( Node<AnyType> p )
    {
        p.next.prev = p.prev;
        p.prev.next = p.next;
        theSize--;
        modCount++;
        
        return p.data;
    }
    
    /**
     * Returns a String representation of this collection.
     */
    public String toString( )
    {
        StringBuilder sb = new StringBuilder( "[ " );

        for( AnyType x : this )
            sb.append( x + " " );
        sb.append( "]" );

        return new String( sb );
    }

    /**
     * Obtains an Iterator object used to traverse the collection.
     * @return an iterator positioned prior to the first element.
     */
    public java.util.Iterator<AnyType> iterator( )
    {
        return new LinkedListIterator( );
    }

    /**
     * This is the implementation of the LinkedListIterator.
     * It maintains a notion of a current position and of
     * course the implicit reference to the MyLinkedList.
     */
    private class LinkedListIterator implements java.util.Iterator<AnyType>
    {
        private Node<AnyType> current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;
        
        public boolean hasNext( )
        {
            return current != endMarker;
        }
        
        public AnyType next( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !hasNext( ) )
                throw new java.util.NoSuchElementException( ); 
                   
            AnyType nextItem = current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }
        
        public void remove( )
        {
            if( modCount != expectedModCount )
                throw new java.util.ConcurrentModificationException( );
            if( !okToRemove )
                throw new IllegalStateException( );
                
            MyLinkedList.this.remove( current.prev );
            expectedModCount++;
            okToRemove = false;       
        }
    }

    /**
     * This is the doubly-linked list node.
     */
    private static class Node<AnyType>
    {
        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;
    }
    
    private int theSize;
    private int modCount = 0;
    private Node<AnyType> beginMarker;
    private Node<AnyType> endMarker;

/*------------------------------------------------------END OF MARK ALLEN WEISS' CODE-------------------------------------------------*/

/*------------------------------------------------------PROJECT 1 CODE BEGINS HERE----------------------------------------------------*/

    /**
     * Swaps the nodes at given indexes
     * @param idx1 @param idx2 the node-indices to be swapped.
     * @return true.
     * @throws IndexOutOfBoundsException if any given index is out of size.
     */
    
     public Boolean swap(int idx1, int idx2){
        swapNodes(idx1,idx2);
        return true;
    }

    /**
     * Swaps the nodes at given indexes
     * @param idx1 @param idx2 the node-indices to be swapped.
     * * @throws IndexOutOfBoundsException if any given index is out of size.
     * checks if the nodes are adjacent and adjusts pointers accordingly
     * if the nodes are non adjacent then adjusts the pointers accordingly
     * swaps the nodes correctly even when entered indices are not in ascending order.
     * @return true.
     */

    private Boolean swapNodes(int idx1, int idx2){
        if(idx1 > size() || idx1 < 0 || idx2 > size() || idx2 < 0){
            throw new IndexOutOfBoundsException( "Your entered index is out of the size of the list");
        }
        if(idx1>idx2){
            int temp;
            temp = idx2;
            idx2 = idx1;
            idx1 = temp;
        } 
        if(idx1 - idx2 == -1 || idx1 - idx2 == 1 ){
                Node<AnyType> node1 = getNode(idx1);
                Node<AnyType> node2 = getNode(idx2);
                Node<AnyType> bufferNode = new Node<>(null,node1.prev, node2.next);
                node2.next = node1;
                node1.prev = node2;
                node1.next = bufferNode.next;
                node2.prev = bufferNode.prev;
                node2.prev.next = node2;
                node1.next.prev = node1; 
            }
            else{
            Node<AnyType> node1 = getNode(idx1);
            Node<AnyType> node2 = getNode(idx2);
            Node<AnyType> bufferNode = new Node<>(null,node1.prev,node1.next);
            node1.prev = node2.prev;
            node1.next = node2.next;
            node2.prev = bufferNode.prev;
            node2.next = bufferNode.next;
            node2.next.prev = node2;
            node2.prev.next = node2;
            node1.prev.next = node1;
            node1.next.prev = node1;
        }
        return true;
    }

    /**
     * shifts the list towards the head or tail according to given @param noOfShifts
     * @return true.
     */ 

    public boolean shift(int noOfShifts){
        shiftList(noOfShifts);
        return true;
    }

    /**
     * shifts the list towards the head or tail according to given @param noOfShifts is positive or negative respectively
     * @return true.
     * shifts it appropriately even if the @param noOfShifts is greater than size of given list
     * Adjusts the pointers using first node, last node and the node at @param noOfShifts (k)
     */ 

    private boolean shiftList(int noOfShifts){
        if(noOfShifts > 0){
            if(noOfShifts > size()){
                noOfShifts = noOfShifts % size();
            }
            int k = noOfShifts;
            Node<AnyType> kthNode = getNode(k);
            endMarker.prev.next = beginMarker.next;
            beginMarker.next.prev = endMarker.prev;
            beginMarker.next = kthNode;
            kthNode.prev.next = endMarker;
            endMarker.prev = kthNode.prev;
            kthNode.prev = beginMarker;

        }else{
            if((noOfShifts * -1) > size()){
                noOfShifts = noOfShifts % size();
            }
            int k = (size()- (noOfShifts*-1));
            Node<AnyType> kthNode = getNode(k);
            endMarker.prev.next = beginMarker.next;
            beginMarker.next.prev = endMarker.prev;
            beginMarker.next = kthNode;
            kthNode.prev.next = endMarker;
            endMarker.prev = kthNode.prev;
            kthNode.prev = beginMarker;
        }
        return true;
    }

    /**
     * removes the elements from @param startIdx to the location (@param startIdx +  @param totalElements)
     * @throws IndexOutOfBoundsException if the startIdx or startIdx + totalElements if greater than size or less than 0
     * @return true.
     */ 

    public boolean erase(int startIdx, int totalElements){
        eraseElements(startIdx, totalElements);
        return true;
}

    /**
     * removes the elements from @param startIdx to the location (@param startIdx +  @param totalElements)
     * @throws IndexOutOfBoundsException if the startIdx or startIdx + totalElements if greater than size or less than 0
     * Adjusts the pointers using Nodes at startIdx and end startIdx+totalElements.
     * @return true.
     */ 

    private boolean eraseElements(int startIdx, int totalElements){
        if(startIdx < 0 || startIdx > size() || startIdx + totalElements  > size() || startIdx + totalElements < 0){
            throw new IndexOutOfBoundsException( "Your entered index is out of the size of the list");
        }else{
            Node<AnyType> startNode = getNode(startIdx);
            Node<AnyType> endNode = getNode(startIdx+totalElements);
            startNode.prev.next = endNode.next;
        }
        return true;
    }

    /**
     * Inserts the @param lst at the given @param idx
     * @throws IndexOutOfBoundsException if the given @param idx is out of size or less than 0
     * @return true.
     */ 
    public boolean insertList(MyLinkedList<AnyType> lst, int idx ){
        insertListAtIndex(lst, idx);
        return true;
    }

    /**
     * Inserts the @param lst at the given @param idx
     * @throws IndexOutOfBoundsException if the given @param idx is out of size or less than 0
     * Attaches the given list to the Node at @param idx and makes adjustments.
     * @return true.
     */ 
    private boolean insertListAtIndex(MyLinkedList<AnyType> inp, int idx){
        if (idx<0 || idx > size() - 1){
            throw new IndexOutOfBoundsException( "Your entered index is out of the size of the list");
        }
        else{
            Node<AnyType> kthNode = getNode(idx);
            inp.endMarker.prev.next = kthNode.next;
            kthNode.next.prev = inp.endMarker.prev;
            inp.beginMarker.next.prev = kthNode;
            kthNode.next = inp.beginMarker.next;
            
        }
        return true;
    }

}
/*--------------------------------------------------END OF PROJECT 1 CODE -----------------------------------------------------------------*/

/*---------------------------------------THE CLASS BELOW DEMONSTRATES THE CODE IMPLEMENTED ABOVE ------------------------------------------ */

class TestLinkedList
{
    public static void main( String [ ] args )
    {
        
        MyLinkedList<Integer> lst = new MyLinkedList<>( );
        MyLinkedList<Integer> lst2 = new MyLinkedList<>();
        for( int i = 1; i < 10; i++ )
                lst2.add( i * 10);

        for( int i = 0; i < 10; i++ )
                lst.add( i );
        for( int i = 20; i < 30; i++ )
                lst.add( 0, i );
        System.out.println( "--------------------------Current List---------------------------" );
        System.out.println( lst );
        
        System.out.println("-------------------------Swap Demo---------------------------------");
        System.out.println( "After Swapping at 0,2" );
        lst.swap(2, 0);
        System.out.println(lst);
        System.out.println( "After Swapping at 1,0" );
        lst.swap(1, 0);
        System.out.println(lst);

        System.out.println("----------------------------Erase Demo------------------------------");
        System.out.println( lst );
        lst.erase(0, 9);
        System.out.println("Erase elements through index 0 and 9 elements after");
        System.out.println(lst);
        
        System.out.println("-----------------------------Shift Demo-----------------------------");
        System.out.println("Shift forward (towards head) with argument 5");
        lst.shift(5);
        System.out.println(lst);
        System.out.println("Shift backward (towards tail with argument -5");
        lst.shift(-5);
        System.out.println(lst);

        System.out.println("--------------------------Insert List Demo---------------------------");
        System.out.println("Current List");
        System.out.println(lst);
        System.out.println("list to be inserted");
        System.out.println(lst2);
        System.out.println("Inserting the above list at index 2");
        lst.insertList(lst2, 2);
        System.out.println(lst);
        System.out.println("----------End of Demonstration------------------");
    }
}