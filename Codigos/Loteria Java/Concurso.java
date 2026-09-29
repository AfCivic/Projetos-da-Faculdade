

class Concurso extends Loteria{
    protected int id;
    protected int tam;
    protected int preço;
    protected int sorteado;
    protected int lucro;
    
    protected Concurso(int id, int tam, int preço){
        this.id = id;
        this.tam = tam;
        this.preço = preço;
        this.sorteado = (int)(Math.random() * tam+1);
        this.lucro = 0;
    }
    
}