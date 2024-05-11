public class Main {
    public static void main(String[] args) {
        BTree bTree = new BTree();

        for (int i = 0; i < 10; i++)
            bTree.inserir(i,i);

        bTree.inOrdem();
        System.out.println();
        for (int i = 3; i < 7; i++) {
            bTree.excluir(i);
        }

        bTree.inOrdem();
        System.out.println();
    }
}
