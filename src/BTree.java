public class BTree
{
    private No raiz;

    public BTree()
    {
        raiz = null;
    }

    private No navegarAteFolha(int info)
    {
        No atual = raiz;
        int pos;
        while (atual.getvLig(0)!=null)
        {
            pos = atual.procurarPosicao(info);
            atual = atual.getvLig(pos);
        }
        return atual;
    }

    private No localizarPai(No folha, int info)
    {
        No atual = raiz;
        No pai = atual;
        int pos;
        while(atual != folha)
        {
            pai = atual;
            pos = atual.procurarPosicao(info);
            atual = atual.getvLig(pos);
        }
        return pai;
    }

    private void split(No folha, No pai)
    {
        No cx1 = new No();
        No cx2 = new No();
        for(int i=0; i<No.n; i++)
        {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setvLig(i, folha.getvLig(i));
        }
        cx1.setvLig(No.n, folha.getvLig(No.n));
        cx1.setTl(No.n);

        for(int i=No.n+1; i<2*No.n+1 ; i++)
        {
            cx2.setvInfo(i-(No.n+1), folha.getvInfo(i));
            cx2.setvPos(i-(No.n+1), folha.getvPos(i));
            cx2.setvLig(i-(No.n+1), folha.getvLig(i));
        }
        cx2.setvLig(No.n, folha.getvLig(2*No.n+1));
        cx2.setTl(No.n);

        if(pai==folha)
        {
            folha.setvInfo(0, folha.getvInfo(No.n));
            folha.setvPos(0, folha.getvPos(No.n));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        }
        else
        {
            int pos = pai.procurarPosicao(folha.getvInfo(No.n));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(No.n));
            pai.setvPos(pos, folha.getvPos(No.n));
            pai.setTl(pai.getTl()+1);
            pai.setvLig(pos, cx1);
            pai.setvLig(pos+1, cx2);
            if(pai.getTl()>2*No.n)
            {
                folha=pai;
                pai=localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    public void inserir(int info, int posArq)
    {
        No folha, pai;
        int pos;
        if(raiz == null)
            raiz = new No(info,posArq);
        else
        {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl() + 1);
            if(folha.getTl() > 2*No.n)
            {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public void inOrdem()
    {
        in_ordem(raiz);
    }

    private void in_ordem(No no)
    {
        if(no!=null)
        {
            for(int i=0; i<no.getTl(); i++)
            {
                in_ordem(no.getvLig(i));
                System.out.println(no.getvInfo(i));
            }
            in_ordem(no.getvLig(no.getTl()));
        }
    }

    private No localizarNo(int info){
        return null;
    }
     private No localizarSubE(No no, int pos){
        no = no.getvLig(pos);
        while(no.getvLig(0)!=null)
            no = no.getvLig(no.getTl());
        return no;
    }
     private No localizarSubD(No no, int pos){
        no = no.getvLig(pos);
        while(no.getvLig(0)!=null)
            no = no.getvLig(0);
        return no;
    }
    private void redistribuirConcatenar(No folha) {
       No pai = localizarPai(folha, folha.getvInfo(0));
       int posPai = pai.procurarPosicao(folha.getvInfo(0));
       No irmaE=null, irmaD=null;
       if(posPai-1>=0)
           irmaE = pai.getvLig(posPai-1);
       if(posPai+1<=pai.getTl())
           irmaD = pai.getvLig(posPai+1);
       if(irmaE!=null && irmaE.getTl()>No.n){
            folha.remanejar(0);
            folha.setvInfo(0, pai.getvInfo(posPai-1));
            folha.setvPos(0, pai.getvPos(posPai-1));
            folha.setTl(folha.getTl()+1);
            pai.setvInfo(posPai-1, irmaE.getvInfo(irmaE.getTl()-1));
            pai.setvPos(posPai-1, irmaE.getvPos(irmaE.getTl()-1));
            folha.setvLig(0, irmaE.getvLig(irmaE.getTl()));
            irmaE.setTl(irmaE.getTl()-1);
       }else if (irmaD!=null && irmaD.getTl()>No.n){
            folha.setvInfo(folha.getTl(), pai.getvInfo(posPai));
            folha.setvPos(folha.getTl(), pai.getvPos(posPai));
            folha.setTl(folha.getTl()+1);
            pai.setvInfo(posPai, irmaD.getvInfo(0));
            pai.setvPos(posPai, irmaD.getvPos(0));
            folha.setvLig(folha.getTl(), irmaD.getvLig(0));
            irmaD.remanejarExclusao(0);
            irmaD.setTl(irmaD.getTl()-1);
       }else{
            if(irmaE!=null){
                irmaE.setvInfo(irmaE.getTl(), pai.getvInfo(posPai-1));
                irmaE.setvPos(irmaE.getTl(), pai.getvPos(posPai-1));
                irmaE.setTl(irmaE.getTl()+1);
                pai.remanejarExclusao(posPai-1);
                pai.setTl(pai.getTl()-1);
                pai.setvLig(posPai-1, irmaE);
                for (int i = 0; i < folha.getTl(); i++) {
                    irmaE.setvInfo(irmaE.getTl(), folha.getvInfo(i));
                    irmaE.setvPos(irmaE.getTl(), folha.getvPos(i));
                    irmaE.setvLig(irmaE.getTl(), folha.getvLig(i));
                    irmaE.setTl(irmaE.getTl()+1);
                }
                irmaE.setvLig(irmaE.getTl(), folha.getvLig(folha.getTl()));
            }else{

            }
            if(raiz==pai && pai.getTl()==0){
                if(irmaE!=null)
                    raiz=irmaE;
                else
                    raiz=irmaD;
            }
            else if(raiz!=pai && pai.getTl()<No.n){
                redistribuirConcatenar(pai);
            }
       }
    }
    public void exclusao(int info){
        No subE, subD, folha;
        No no = localizarNo(info);
        int pos;
        if(no!=null){
            pos = no.procurarPosicao(info);
            if(no.getvLig(0)!=null){
                subE = localizarSubE(no, pos);
                subD = localizarSubD(no, pos+1);
                if(subE.getTl()>No.n || subD.getTl()==No.n){
                    no.setvInfo(pos, subE.getvInfo(subE.getTl()-1));
                    no.setvPos(pos, subE.getvPos(subE.getTl()-1));
                    folha = subE;
                    pos = subE.getTl()-1;
                }else{
                    no.setvInfo(pos, subD.getvInfo(0));
                    no.setvPos(pos, subD.getvPos(0));
                    folha = subD;
                    pos = 0;
                }
            }
            else
                folha = no;
            folha.remanejarExclusao(pos);
            folha.setTl(no.getTl()-1);

            if(folha == raiz && folha.getTl()==0)
                raiz=null;
            else if(folha != raiz && folha.getTl()<No.n)
                redistribuirConcatenar(folha);

        }
    }

}
