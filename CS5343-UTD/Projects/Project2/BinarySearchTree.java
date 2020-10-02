// BinarySearchTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an unbalanced binary search tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
class UnderflowException extends RuntimeException
{
}

public class BinarySearchTree<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the tree.
     */
    public BinarySearchTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x )
    {
        root = remove( x, root );
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if not found.
     */
    public boolean contains( AnyType x )
    {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> insert( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return new BinaryNode<>( x, null, null );
        
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult > 0 )
            t.right = insert( x, t.right );
        else
            ;  // Duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<AnyType> remove( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return t;   // Item not found; do nothing
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            t.left = remove( x, t.left );
        else if( compareResult > 0 )
            t.right = remove( x, t.right );
        else if( t.left != null && t.right != null ) // Two children
        {
            t.element = findMin( t.right ).element;
            t.right = remove( t.element, t.right );
        }
        else
            t = ( t.left != null ) ? t.left : t.right;
        return t;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the smallest item.
     */
    private BinaryNode<AnyType> findMin( BinaryNode<AnyType> t )
    {
        if( t == null )
            return null;
        else if( t.left == null )
            return t;
        return findMin( t.left );
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the subtree.
     * @return node containing the largest item.
     */
    private BinaryNode<AnyType> findMax( BinaryNode<AnyType> t )
    {
        if( t != null )
            while( t.right != null )
                t = t.right;

        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     * @return node containing the matched item.
     */
    private boolean contains( AnyType x, BinaryNode<AnyType> t )
    {
        if( t == null )
            return false;
            
        int compareResult = x.compareTo( t.element );
            
        if( compareResult < 0 )
            return contains( x, t.left );
        else if( compareResult > 0 )
            return contains( x, t.right );
        else
            return true;    // Match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the subtree.
     */
    private void printTree( BinaryNode<AnyType> t )
    {
        if( t != null )
        {
            printTree( t.left );
            System.out.println( t.element );
            printTree( t.right );
            
        }
    }

    /**
     * Internal method to compute height of a subtree.
     * @param t the node that roots the subtree.
     */
    private int height( BinaryNode<AnyType> t )
    {
        if( t == null )
            return -1;
        else
            return 1 + Math.max( height( t.left ), height( t.right ) );    
    }
    
    public int nodeCount(){
        if (isEmpty())
            return 0;
        else return nodeCount(root);
    }

    private int nodeCount(BinaryNode<AnyType> t){
        int count = 1;
        if(t.left != null){
            count += nodeCount(t.left);
        }
        if(t.right != null){
            count += nodeCount(t.right);
        }
        return count;
    }

    public boolean isFull(){
        if(isEmpty())
            return true;
        
        else return isFull(root);
    }

    private boolean isFull(BinaryNode<AnyType> t){
        if(t.left == null && t.right == null) return true;
        if (t.left != null && t.right != null){
            return  (isFull(t.left) && isFull(t.right));
        }
        return false;
    }

    public boolean compareStructure(BinarySearchTree<AnyType> t2){
        if(root ==null && t2.root == null){
            return true;
        }
        return compareStructure(root, t2.root);

    }

    private boolean compareStructure(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2){
        if(t1 == null && t2 == null) return true;
        if (t1 != null & t2 != null){
            return (
                compareStructure(t1.left, t2.left) &&
                compareStructure(t1.right, t2.right) 
            );
        }
        return false;
    }
    
    public boolean equals(BinarySearchTree<AnyType> t2){
        if(root == null && t2.root == null ){
            return true;
        }
        return equals(root, t2.root);
    }

    private boolean equals(BinaryNode<AnyType> t1, BinaryNode<AnyType> t2){
        if(t1 == null && t2 == null) return true;
        if(t1 != null && t2 !=null) {
            return(
                t1.element == t2.element &&
                equals(t1.left, t2.left) &&
                equals(t1.right, t2.right)
            );
        }
        return false;
    }

    public BinarySearchTree<AnyType> copy(){
        BinarySearchTree<AnyType> t2= new BinarySearchTree<>();
        t2.root = copy(root, t2.root);
        return t2;
    }

    private BinaryNode<AnyType> copy(BinaryNode<AnyType>t1, BinaryNode<AnyType> t2){
        if(t1 == null)return null;
        BinaryNode<AnyType> copy = new BinaryNode<AnyType>(t1.element,null,null);
        copy.left = copy(t1.left,copy);
        copy.right = copy(t1.right,copy);
        return copy;
    }

    public BinarySearchTree<AnyType> mirror(){
        BinarySearchTree<AnyType> t2 = new BinarySearchTree<>();
        t2.root = mirror(root);
        return t2;
    }

    private BinaryNode<AnyType> mirror(BinaryNode<AnyType>t1){
        if(t1==null)return null;
        BinaryNode<AnyType> mirrorNode = new BinaryNode<AnyType>(t1.element,null,null);
        mirrorNode.left = mirror(t1.right);
        mirrorNode.right = mirror(t1.left);
        return mirrorNode;
    }

    public boolean isMirror(BinarySearchTree<AnyType> t2){
        BinarySearchTree<AnyType> mirrorTree = mirror();
        return equals(t2.root,mirrorTree.root);
    }

    public boolean rotateRight(){
        if(root.left == null) return true;
       root = rotateRight(root);
        return true;
    }

    private BinaryNode<AnyType> rotateRight(BinaryNode<AnyType> root){
        BinaryNode<AnyType> x = root.left;
        BinaryNode<AnyType> z = x.right;
        x.right = root;
        root.left = z;
        return x;
    }

    public boolean rotateLeft(){
        if(root.left == null) return true;
       root = rotateLeft(root);
        return true;
    }

    private BinaryNode<AnyType> rotateLeft(BinaryNode<AnyType> root){
        BinaryNode<AnyType> x = root.right;
        BinaryNode<AnyType> z = x.left;
        x.left = root;
        root.right = z;
        return x;
    }
    // Basic node stored in unbalanced binary search trees
    private static class BinaryNode<AnyType>
    {
            // Constructors
        BinaryNode( AnyType theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
    }


      /** The tree root. */
    private BinaryNode<AnyType> root;


        // Test program
    public static void main( String [ ] args )
    {
        BinarySearchTree<Integer> t = new BinarySearchTree<>( );
        BinarySearchTree<Integer> x = new BinarySearchTree<>();
        x.insert(2);
        x.insert(1);
        x.insert(3);
        System.out.println(x.isFull());
        // final int NUMS = 4000;
        // final int GAP  =   37;

        // System.out.println( "Checking... (no more output means success)" );

        // for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
        //     t.insert( i );

        System.out.println(t.isFull());
        for(int i = 0; i<10;i++){
            t.insert(i);
        }
        // t.printTree();
        System.out.println(t.nodeCount());
        System.out.println(t.isFull());
        System.out.println("---");
        System.out.println(t.compareStructure(t));
        System.out.println("---");
        System.out.println(t.equals(t));
        x = t.mirror();
        x.printTree();
        System.out.println("---");
        System.out.println(x.isMirror(t));
        System.out.println("---");
        t.insert(-1);
        t.rotateRight();
        t.printTree();
        t.rotateLeft();
        t.printTree();
        // t.printTree();
        
        // if( NUMS < 40 )
        //     t.printTree( );
        // if( t.findMin( ) != 2 || t.findMax( ) != NUMS - 2 )
        //     System.out.println( "FindMin or FindMax error!" );

        // for( int i = 2; i < NUMS; i+=2 )
        //      if( !t.contains( i ) )
        //          System.out.println( "Find error1!" );

        // for( int i = 1; i < NUMS; i+=2 )
        // {
        //     if( t.contains( i ) )
        //         System.out.println( "Find error2!" );
        // }
    }
}