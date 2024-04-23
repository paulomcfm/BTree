public class BTree {
    private No raiz;

    public BTree() {
        raiz = null;
    }

    private No navegarAteFolha(int info){
        No folha = raiz;
        int pos;
        while(folha.getvLig(0)!=null){
            pos = folha.procurarPosicao(info);
            folha = folha.getvLig(pos);
        }
        return folha;
    }

    private No localizarPai(No folha, int info){
        No atual = raiz;
        No pai = atual;
        int pos;
        while(atual!=folha){
            pai=atual;
            pos = atual.procurarPosicao(info);
            atual = atual.getvLig(pos);
        }
        return pai;
    }

    private void split(No folha, No pai){

    }

    public void inserir(int info, int posArq){
        if(raiz==null)
            raiz=new No(info, posArq);
        else{
            No folha = navegarAteFolha(info);
            int pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos,info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl()+1);
            if(folha.getTl()>No.n*2){
                No pai = localizarPai(folha, info);
                split(folha,pai);
            }
        }
    }

    public void inOrdem(){
        in_ordem(raiz);
    }

    private void in_ordem(No raiz) {

    }
}
