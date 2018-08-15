//Rick _ arbin gen

public class Tree
{
  public static void main(String [] args)
  {
    test();
  }

  public static void test()
  {
    ArBin<Integer> ab = new ArBin<>(new Node<Integer>(new Integer(50)));
    java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
    for (int i=0;i<10 ;i++ )
    {
      list.add(new Integer((int)(Math.random()*101)));
    }
    for (Integer i : list)
    {
        System.out.println("nuevo num: "+i);
    }

    ab.add(list);
    System.out.println("preorden:");
    ab.preorden();
    System.out.println("inorden:");
    ab.inorden();
    System.out.println("postorden:");
    ab.postorden();
    System.out.println("Niveles:");
    ab.levels();
  }
}

/**
  *defines an AVL tree
**/
class ArBin<T extends Comparable>
{
  public ArBin()
  {
    //pass
  }

  public ArBin(T root_info)
  {
    this(new Node<T>(root_info));
  }

  public ArBin(Node<T> root)
  {
    this.root = root;
  }

  //METHODS_____________________________________________________________________

  //operaciones básicas

  public void add(Node<T> nodo)
  {
    add(this.root,nodo);
  }

  public void add(T info)
  {
    add(this.root,new Node<T>(info));
  }

  public void add(Node<T> root, Node<T> nodo)
  {
    int compare = root.getInfo().compareTo(nodo.getInfo());
    if(compare > 0)
    {
      if (root.getLeft() == null) {root.setLeft(nodo);}
      else{add(root.getLeft(),nodo);}
    }
    else if(compare < 0)
    {
      if (root.getRight() == null) {root.setRight(nodo);}
      else{add(root.getRight(),nodo);}
    }
    else
    {
      System.out.println("elemento similar descartado "+nodo);
    }

    if (compare != 0)
    {
      setFactorBalance(root);
      setFactorBalance(root.getLeft());
      setFactorBalance(root.getRight());

      System.out.println("factores de valance:");
      System.out.println(getFactorBalance(root));
      System.out.println(getFactorBalance(root.getLeft()));
      System.out.println(getFactorBalance(root.getRight()));

      if (getFactorBalance(root) == -2 && getFactorBalance(root.getLeft())!=1)
      {
        System.out.println("caso 1 de valance");
        root.clone(rotacionDerecha(root));
        //System.out.println(root);
      }
      else if (getFactorBalance(root) == 2 && getFactorBalance(root.getRight())!=-1)
      {
        System.out.println("caso 2 de valance");
        root.clone(rotacionIzquierda(root));
        //System.out.println(root);
      }
      else if (getFactorBalance(root) == 2 && getFactorBalance(root.getRight())==-1)
      {
        System.out.println("caso 3 de valance");
        root.clone(rotacionDobleIzquierda(root));
        //System.out.println(root);
      }
      else if (getFactorBalance(root) == -2 && getFactorBalance(root.getLeft())==1)
      {
        System.out.println("caso 4 de valance");
        root.clone(rotacionDobleDerecha(root));
        //System.out.println(root);
      }
      else
      {
        System.out.println("rotación estable");
      }
    }

  }

  public void add(Iterable<T> list)
  {
    for (T t : list)
    {
      add(this.root,new Node<T>(t));
    }
  }

  //recorridos

  public void preorden()
  {
    preorden(this.root);
  }

  private void preorden(Node<T> root)
  {
    if (root != null)
    {
      System.out.println(root);
      preorden(root.getLeft());
      preorden(root.getRight());
    }
  }

  public void inorden()
  {
    inorden(this.root);
  }

  private void inorden(Node<T> root)
  {
    if (root != null)
    {
      inorden(root.getLeft());
      System.out.println(root);
      inorden(root.getRight());
    }
  }

  public void postorden()
  {
    postorden(this.root);
  }

  private void postorden(Node<T> root)
  {
    if (root != null)
    {
      postorden(root.getLeft());
      postorden(root.getRight());
      System.out.println(root);
    }
  }

  public void levels()
  {
    levels(this.root);
  }

  public void levels(Node<T> root)
  {
    java.util.ArrayList<Node<T>> cola = new java.util.ArrayList<>();
    cola.add(root);
    while(!cola.isEmpty())
    {
        System.out.println(cola.get(0));
        if (cola.get(0).getLeft() != null)
        {
            cola.add(cola.get(0).getLeft());
        }
        if (cola.get(0).getRight() != null)
        {
            cola.add(cola.get(0).getRight());
        }
        cola.remove(0);
    }
  }

  //rotaciones

  public int getAltura(Node<T> root)
  {
    if (root == null){return -1;}
    return (1 + Math.max(getAltura(root.getLeft()), getAltura(root.getRight())));
  }

  public int getFactorBalance(Node<T> root)
  {
    if (root == null){return 0;}
    return root.getBF();
  }

  void setFactorBalance(Node<T> root)
  {
      if (root != null)
      {
          root.setBF(getAltura(root.getRight()) - getAltura(root.getLeft()));
      }
  }

  private Node<T> rotacionDerecha(Node<T> x)
  {
    Node<T> h = x.getLeft();
    x.setLeft(h.getRight());
    h.setRight(x);

    setFactorBalance(x);
    setFactorBalance(h);

    return h;
  }

  private Node<T> rotacionDobleDerecha(Node<T> x)
  {
      x.setLeft(rotacionIzquierda(x.getLeft()));
      return rotacionDerecha(x);
  }

  Node<T> rotacionIzquierda(Node<T> x)
  {
    Node<T> h = x.getRight();
    x.setRight(h.getLeft());
    h.setLeft(x);

    setFactorBalance(x);
    setFactorBalance(h);

    return h;
  }

  Node<T> rotacionDobleIzquierda(Node<T> x)
  {
    x.setRight(rotacionDerecha(x.getRight()));
    return rotacionIzquierda(x);
  }

  //ATRIB_______________________________________________________________________

  private Node<T> root = new Node();
  private int altura,
              peso;

  public void setRoot(Node<T> root)
  {
    this.root = root;
  }

  public Node<T> getRoot()
  {
    return this.root;
  }

  public int getPeso()
  {
    return this.peso;
  }

  public ArBin<T> getRArBin()
  {
    return new ArBin<T>(this.root.getRight());
  }

  public ArBin<T> getLArBin()
  {
    return new ArBin<T>(this.root.getLeft());
  }

}


/**
  *defines a generic ABB node
**/
final class Node<T extends Comparable>
{
  public Node()
  {
    //pass
  }
  public Node(T info)
  {
    this.info = info;
  }

  @Override
  public String toString()
  {
    return "Node: " + this.info.toString();
  }

  public void clone(Node<T> nodo)
  {
    this.setInfo(nodo.getInfo());
    this.setLeft(nodo.getLeft());
    this.setRight(nodo.getRight());
  }

  //ATRIB_______________________________________________________________________

  private Node<T> left;
  private Node<T> right;
  private T info;
  private int BF = 0;

  public void setLeft(Node<T> left)
  {
    this.left = left;
  }

  public void setRight(Node<T> right)
  {
    this.right = right;
  }

  public void setInfo(T info)
  {
    this.info = info;
  }

  public Node<T> getLeft()
  {
    return this.left;
  }

  public Node<T> getRight()
  {
    return this.right;
  }

  public T getInfo()
  {
    return this.info;
  }

  public int getBF()
  {
    return this.BF;
  }

  public void setBF(int bf)
  {
    this.BF = bf;
  }

}
