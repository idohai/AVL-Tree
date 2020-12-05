/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 *
 */
//as
public class AVLTree {
  private IAVLNode ext = new AVLNode(-1);
  private IAVLNode root;

  public AVLTree(){
  }
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
    if (this.getRoot() == null){
      return false;
    }
    return true;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k) {
    IAVLNode node = this.getRoot();
    return search_rec(node, k);
  }

  private String search_rec(IAVLNode node, int k){
  if (node == null)
    return null;
  else if (node.getKey() == k)
    return node.getValue();
  else if (node.getKey() < k)
    return search_rec(node.getLeft(), k);
  else //(node.getKey() > k)
    return search_rec(node.getRight(), k);
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the AVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
   * returns -1 if an item with key k already exists in the tree.
   */
     public int insert(int k, String i) {
  //     IAVLNode x = new AVLNode(k,i);
  //     IAVLNode y = treePosition(this.getRoot(),k);
  //     IAVLNode z = y.getParent();
  //     if (k < y.getKey()) {
  //       y.setLeft(x);
  //     } else {
  //       y.setRight(x);
  //     }
  //     y.setHeight(Math.max(1,y.getHeight()));
  //     int nodeBalance = z.getNodeBalance();
  //     if (abs(nodeBalance()) <= 1){ // is the tree unbalanced?
  //       if (nodeBalance > 0){ // left heavy
  //         if (x.getKey() < y.getKey()){
  //           leftRotate(x, y);
  //         }
  //         else{
  //           rightRotate(leftRotate(x, y), z);
  //         }

  //       }

  //       else{ // right heavy
  //       }
  //     }
  //     if (x.getKey() <y.getKey() && y.getKey()<z.getKey()){
      

  //     while (z.height() == y.getHeight()){
  //       z.setHeight(z.getHeight() + 1);
  //       y = z;
  //       z = z.getParent;
  //     }
  //     while (abs(y.getNodeBalance()) <= 1){
  //     if (x.getKey() <y.getKey() && y.getKey()<z.getKey()){

  //     }

  //     if (y.getParent()!=null && y.getParent().getHeight()==1) {
  //       return Balance(x,y);
  //     }
  //   }
      int opCnt = 0;
      IAVLNode x = new AVLNode(k,i);
      if (this.root == null){
        this.root = x;
        return 0;
      }
      IAVLNode position = treePosition(root, k);
      if (k == position.getKey()){
        return -1;
      }
      else{
        if (position.isLeaf()){
          position.setHeight(1);
        }
        if (k<position.getKey()){
          position.setLeft(x);
          x.setParent(position);
        }
        else{
          position.setRight(x);
          x.setParent(position);
        }
      }
      return opCnt;
  // }
}

  // static private IAVLNode rightRotate (IAVLNode x, IAVLNode y){ // x is son of y
  //   IAVLNode z = y.getParent;
  //   y.setParent(z.getParent());
  //   z.setLeft(y.getRight());
  //   z.setParent(y);
  //   z.setHeight(y.getHeight());
  //   y.setHeight(y.getHeight() + 1);
  //   return y;
  // }

  // static private IAVLNode leftRotate(IAVLNode x, IAVLNode y){ //x is son of y
  //   IAVLNode z = y.getParent();
  //   y.setParent(z.getParent());
  //   z.setRight(y.getLeft());
  //   z.setParent(y);
  //   y.setLeft(z);
  //   z.setHeight(y.getHeight());
  //   y.setHeight(y.getHeight() + 1);
  //   return y;
  // }

  // static private IAVLNode rightRotate (IAVLNode x, IAVLNode y){ // x is son of y
  //   IAVLNode r = y.getParent();
  //   x.setParent(r);
  //   y.setLeft(x.getRight());
  //   x.setRight(y);
  //   y.setParent(x);
  //   y.getLeft().setParent(y);
  //   x.setHeight(x.getHeight() + 1);
  //   y.setHeight(y.getHeight() -1);
  //   return x;
  // }
    
  private int rightRotate(IAVLNode a){ // a is top node to rotate
    int opCnt = 1;
    IAVLNode p = a.getParent();
    IAVLNode b = a.getLeft();
    a.setLeft(b.getRight());   //a.left = b.right child
    if (b.getRight() != ext){
      b.getRight().setParent(a); //b.rightchild parent = a.
    }
    b.setRight(a);             //b.rightchild = a
    a.setParent(b);            //a.parent =b
    b.setParent(p);             //b.parent = p
    if (p != null){            //set p.child to be b
      if (p.getLeft() == a){
        p.setLeft(b);
      }
      else{
        p.setRight(b);
      }
    }
    else{
      this.root = b;
    }
    opCnt += a.updateHeight();
    opCnt += b.updateHeight();
    return opCnt;
  }

  private int leftRotate(IAVLNode a){
    int opCnt = 1;
    IAVLNode p = a.getParent();
    IAVLNode b = a.getRight();
    a.setRight(b.getLeft());
    if (b.getLeft() != ext){
      b.getLeft().setParent(a);
    }
    b.setLeft(a);
    a.setParent(b);
    b.setParent(p);
    if (p != null){
      if (p.getLeft() == a){
        p.setLeft(b);
      }
      else{
        p.setRight(b);
      }
    }
    else{
      this.root = b;
    }
    opCnt += a.updateHeight();
    opCnt += b.updateHeight();
    return opCnt;
  }

  private void leftLeft(IAVLNode a){
    rightRotate(a);
  }

  private void leftRight(IAVLNode a){
    leftRotate(a.getLeft());
    rightRotate(a);
  }

  private void rightRight(IAVLNode a){
    leftRotate(a);
  }

  private void rightLeft(IAVLNode a){
    rightRotate(a.getRight());
    leftRotate(a);
  }

  private static IAVLNode treePosition(IAVLNode x, int k) {
    IAVLNode y = x;
    while (x != null){
      if (x.getKey() == -1){
        break;
      }
      y = x;
      if (k == x.getKey()){
        return x;
      }
      else if (k < x.getKey()){
        x = x.getLeft();
      }
      else{
        x = x.getRight();
      }
    }
    return y;
  }

  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * demotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   return 42;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max() //update in insert + delete
   {
	   return "42"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return 42; // to be replaced by student code
   }
   
     /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    *
    * precondition: none
    * postcondition: none
    */
   public IAVLNode getRoot()
   {
	   return this.root;
   }
     /**
    * public string split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
	  * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x)
   {
	   return null; 
   }
   /**
    * public join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	  * precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   public int join(IAVLNode x, AVLTree t)
   {
	   return 0; 
   }
// MINEEEEEE
   // public String printTree(IAVLNode root, int position){
   //  if (root == null){
   //    return null;
   //  }
   //  else{
   //    int i;
   //    String s = "";
   //    for (i; i<100; i++){
   //      if (i == 100/position){
   //        s.concat(root.getKey());
   //      }
   //      else{
   //      s.concat(" ");
   //      }
   //    }
   //      System.out.println(s);
   //      String e = "";
   //      e.concat(root.getLeft());
   //      for (i=0; i<position*2; i++){
   //        e.concat(" ");
   //      }
   //      e.concat(root.getRight());
   //      System.out.println(printTree(e));
   //  }
   // }

  // Function to print binary tree in 2D  
  // It does reverse inorder traversal  
  static void print2DUtil(IAVLNode root, int space, AVLTree tree) { 
    // Base case  
    if (root == null || root == tree.ext)  
        return;  
  
    // Increase distance between levels  
    space += 20;  
  
    // Process right child first  
    print2DUtil(root.getRight(), space, tree);  
  
    // Print current node after space  
    // count  
    System.out.print("\n");  
    for (int i = 20; i < space; i++)  
        System.out.print(" ");  
    System.out.print(root.getKey() + "\n");  
  
    // Process left child  
    print2DUtil(root.getLeft(), space, tree);  
  }  
  
  // Wrapper over print2DUtil()  
  static void print2D(IAVLNode root, AVLTree tree)  
  {  
      // Pass initial space count as 0  
      print2DUtil(root, 0, tree);  
  }  
  

	/**
	   * public interface IAVLNode
	   * ! Do not delete or modify this - otherwise all tests will fail !
	   */
	public interface IAVLNode{	
		public int getKey(); //returns node's key (for virtuval node return -1)
		public String getValue(); //returns node's value [info] (for virtuval node return null)
		public void setLeft(IAVLNode node); //sets left child
		public IAVLNode getLeft(); //returns left child (if there is no left child return null)
		public void setRight(IAVLNode node); //sets right child
		public IAVLNode getRight(); //returns right child (if there is no right child return null)
		public void setParent(IAVLNode node); //sets parent
		public IAVLNode getParent(); //returns the parent (if there is no parent return null)
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node
    public void setHeight(int height); // sets the height of the node
    public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
    public int updateHeight();
    public boolean isLeaf();
	}

   /**
   * public class AVLNode
   *
   * If you wish to implement classes other than AVLTree
   * (for example AVLNode), do it in this file, not in 
   * another file.
   * This class can and must be modified.
   * (It must implement IAVLNode)
   */
  public class AVLNode implements IAVLNode{
		private int key, height;
    private String value;
    private IAVLNode leftNode, rightNode, parent;

    public AVLNode(int key, String value) { //constructor with default: parent = null, height = 0
      this(key, value, 0, null);
    }
    
    public AVLNode(int key, String value, AVLNode parent) { //constructor to create node with a parent
      this(key, value, 0, parent);
    }

    public AVLNode(int key, String value, int height, AVLNode parent) { //constructor to create node with parent and height
      this.key = key;
      this.value = value;
      this.height = height;
      this.parent = parent;
      this.leftNode = ext;
      this.rightNode = ext;
    }

    private AVLNode(int key) { //constructor for extNode. value, leftNode, rightNode, parent = null
      this.key = key;
      this.value = null;
      this.leftNode = null;
      this.rightNode = null;
      this.parent = null;
    }
    
    public int getKey() {
			return this.key;
		}

		public String getValue() {
			return this.value;
		}

		public void setLeft(IAVLNode node) {
      this.leftNode = node;
		}

		public IAVLNode getLeft() {
			return this.leftNode;
		}

		public void setRight(IAVLNode node) {
      this.rightNode = node;
		}

		public IAVLNode getRight() {
      return this.rightNode;
		}

		public void setParent(IAVLNode node) {
      this.parent = node;
		}

		public IAVLNode getParent() {
			return this.parent;
		}

		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode() {
			return (this != ext);
		}

    public void setHeight(int height) {
      this.height = height;
    }

    public int getHeight() {
      return this.height;
    }
    
    public int updateHeight() { //added set height by definition. no input needed
      int height = 1 + Math.max(this.leftNode.getHeight(), this.rightNode.getHeight());
      if (this.height != height){
        this.setHeight(height);
        return 1;
      }
      return 0;
    }

    public int getNodeBalance(){
      return this.leftNode.getHeight() - this.rightNode.getHeight();
    }

    public boolean isLeaf(){
      return (this.getRight() == null && this.getLeft() == null);
    }
  }
    public static void main(String args[]){
    AVLTree tree = new AVLTree();
    tree.insert(5,"");
    tree.insert(1,"");
    tree.insert(3,"");
    tree.insert(2,"");
    tree.insert(7,"");
    System.out.println("tree before rotation: ");
    print2D(tree.getRoot(), tree); 
    tree.rightRotate(tree.getRoot().getLeft().getRight());
    tree.leftRotate(tree.getRoot().getLeft());
    System.out.println("tree after Right-Left Rotation: ");
    print2D(tree.getRoot(), tree);
    System.out.println(treePosition(tree.getRoot(), 7).getKey());
    System.out.println(tree.getRoot().getRight().getHeight());
    // System.out.println (tree.getRoot().getParent());
    // System.out.println(tree.getRoot().getLeft().getKey());
    // tree.rightRotate(tree.getRoot()); 
    // System.out.println("root: " + tree.getRoot().getKey());
    // System.out.println("root.parent: " +tree.getRoot().getParent());
    // System.out.println("root.leftChild: " + tree.getRoot().getLeft().getKey());
    // System.out.println("root.leftChild.parent: " + tree.getRoot().getLeft().getParent().getKey());
    // System.out.println("root.Rightchild: " + tree.getRoot().getRight().getKey());
    // System.out.println("root.Rightchild.parent: " + tree.getRoot().getRight().getParent().getKey());
    // System.out.println("root.rightRightchild: " + tree.getRoot().getRight().getRight().getKey());
    // System.out.println("root.rightRightchild.parent: " + tree.getRoot().getRight().getRight().getParent().getKey());
    // System.out.println (tree.ext.getKey());
    // System.out.println("root.rightchild.leftchild: " + tree.getRoot().getRight().getLeft());
    // print2D(tree.getRoot());
    // tree.leftRotate(tree.getRoot());
    // print2D(tree.getRoot());
    System.out.println("little change");
  }  
}


