//Rick _ arbin gen test 2

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

    System.out.println("\n altura: \n "+ab.altura()+"\n"+ab.getRoot().getBF()+"\n");
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

  public int altura()
  {
    return this.root.altura();
  }

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

  }

  public void add(Iterable<T> list)
  {
    for (T t : list)
    {
      add(this.root,new Node<T>(t));
    }
  }

  public Node<T> search(T info)
  {
    return search(new Node<T>(info));
  }

  public Node<T> search(Node<T> node)
  {
    return search(this.root,node);
  }

  public Node<T> search(Node<T> root, Node<T> one)
  {
    int compare = root.getInfo().compareTo(one.getInfo());
    if(compare > 0)
    {
      if (root.getLeft() == null) {return null;}
      else{return search(root.getLeft(),one);}
    }
    else if(compare < 0)
    {
      if (root.getRight() == null) {return null;}
      else{return search(root.getRight(),one);}
    }
    else
    {
      return root;
    }
  }

  public void del(Node<T> node)
  {
    search(node).del();
  }

  public void del(T info)
  {
    del(new Node<T>(info));
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

  @Override
  public boolean equals(Object o)
  {
    if(o instanceof Node)
    {
      if(((Node<T>)o).getInfo().equals(this.getInfo()))
      {
        return true;
      }
    }
    return false;
  }

  public void clone(Node<T> nodo)
  {
    this.setInfo(nodo.getInfo());
    this.setLeft(nodo.getLeft());
    this.setRight(nodo.getRight());
  }

  public void del()
  {}

  public int altura()
  {
    if(this.getRight()!=null && this.getLeft()!=null)
    {return Math.max(this.getRight().altura(),this.getLeft().altura())+1;}
    else if(this.getRight()==null && this.getLeft()!=null)
    {return this.getLeft().altura()+1;}
    else if(this.getRight()!=null && this.getLeft()==null)
    {return this.getRight().altura()+1;}
    return 0;
  }

  public int getBF()
  {
    if (this.getRight() == null && this.getLeft() == null)
    {return 0;}
    if (this.getRight() == null)
    {return 0 - this.getLeft().altura();}
    if (this.getLeft() == null)
    {return this.getRight().altura();}

    return this.getRight().altura() - this.getLeft().altura();
  }

  //ATRIB_______________________________________________________________________

  private Node<T> left;
  private Node<T> right;
  private T info;

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


}
