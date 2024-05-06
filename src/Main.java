public class Main {
    public static void main(String[] args) {
        BTree bTree = new BTree();

        for (int i = 1; i < 10; i++)
            bTree.inserir(i,i);

        bTree.inOrdem();
    }
}
