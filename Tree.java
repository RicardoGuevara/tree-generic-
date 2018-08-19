//Rick _ arbin gen test 2

public class Tree
{
  public static void main(String [] args)
  {
    test(); // crea un árbol random y prueba todas las funciones
  }

  public static void test()
  {
    // árbol relleno de valores al azar:
    ArBin<Integer> ab = new ArBin<>(new Node<Integer>(new Integer(50)));
    java.util.ArrayList<Integer> list = new java.util.ArrayList<>();
    for (int i=0;i<10 ;i++ )
    {
      list.add(new Integer((int)(Math.random()*101)));
    }
    System.out.println("lista de números random a ser insertados:\nnuevo num: 50");
    for (Integer i : list)
    {
        System.out.println("nuevo num: "+i);
    }
    System.out.println("\ninsertar y valancear:");
    ab.add(list);

    // recorridos
    System.out.println("\npreorden:");  ab.preorden();
    System.out.println("\ninorden:");   ab.inorden();
    System.out.println("\npostorden:"); ab.postorden();
    System.out.println("\nNiveles:");   ab.levels();

    // altura y factor de valance
    System.out.println("\n altura: "+ab.altura()+"\n factorBalance: "+ab.getRoot().getBF());

    // lleno, estricto, completo
    System.out.println("\nverificaciones:");
    System.out.println((ab.estricto())? "árbol estricto"  : "el árbol NO es estricto");
    System.out.println((ab.completo())? "árbol completo"  : "el árbol NO está completo");
    System.out.println((ab.lleno())?    "árbol lleno"     : "el árbol NO está lleno");

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

  //verificaciones lleno, completo, estricto

  public boolean lleno()
  {
    return lleno(this.root);
  }

  public boolean completo()
  {
    return completo(this.root);
  }

  public boolean estricto()
  {
    return estricto(this.root);
  }

  public boolean lleno(Node<T> root)
  {
    // solo en caso de enviar un árbol vacío
    if (root == null)
    {System.out.println("  árbol vacío"); return false;}

    if( root.getBF() == 0)  // comparar altura de ambas ramas (deben ser iguales)
    {
      if (root.getRight() == null && root.getLeft() == null) //se llegó a una hoja
      {
          return true;
      }
      if((root.getRight() != null && root.getLeft() == null) || // solo existe el hijo derecho
            (root.getRight()==null && root.getLeft()!=null)) // solo existe el hijo izquierdo
      {
          return false;
      }
      return lleno(root.getRight()) && lleno(root.getLeft());
    }
    return false;
  }

  public boolean completo(Node<T> root)
  {
    if(root == null)
    {return false;}
    /*  en caso de enviar un árbol de altura menor a 2
        el primer condicional evita un ciclo infinito
        en otro caso, la altura nunca es inferior a 2
    */
    if (root.altura() < 2)
    {
        return true;
    }
    // la altura es = 2 en el ante-penúltimo nivel del árbol
    if (root.altura() == 2)
    {
      if (root.getRight() != null && root.getLeft() != null)
      {
          return true;
      }
      return false;
    }

    return completo(root.getLeft()) && completo(root.getRight());
  }

  public boolean estricto(Node<T> root)
  {
    if  ((root.getRight() != null && root.getLeft() == null) ||
          (root.getRight()==null && root.getLeft()!=null))
    {
      return false;
    }
    if (root.getRight() == null && root.getLeft() == null)
    {
      return true;
    }

    return estricto(root.getRight()) && estricto(root.getLeft());
  }

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

    if(compare != 0 && root.getRight() != null && root.getLeft() !=null)
    {
      switch(root.getBF())
      {
        case -2:
          if (root.getLeft().getBF() == 1)
          {
            root = doble_izquierda(root);
            System.out.println("doble izquierda: " + root);
          }
          else if (root.getLeft().getBF() != 1)
          {
            root = rotacion_izquierda(root);
            System.out.println("simple izquierda: " + root);
          }
        break;
        case 2:
          if (root.getRight().getBF() == -1)
          {
            root = doble_derecha(root);
            System.out.println("doble derecha: " + root);
          }
          else if (root.getLeft().getBF() != -1)
          {
            root = rotacion_derecha(root);
            System.out.println("simple derecha: " + root);
          }
        break;
        default:
          System.out.println("rotación estable" + root);
        break;
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

  public Node<T> rotacion_derecha(Node<T> root)
  {
    Node<T> x = root.getRight();
    root.setRight(x.getLeft());
    x.setLeft(root);
    return x;
  }

  public Node<T> rotacion_izquierda(Node<T> root)
  {
    Node<T> x = root.getLeft();
    root.setLeft(x.getRight());
    x.setRight(root);
    return x;
  }

  public Node<T> doble_derecha(Node<T> root)
  {
    root.setRight(rotacion_izquierda(root.getRight()));
    return rotacion_derecha(root);
  }

  public Node<T> doble_izquierda(Node<T> root)
  {
    root.setLeft(rotacion_derecha(root.getLeft()));
    return rotacion_izquierda(root);
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
