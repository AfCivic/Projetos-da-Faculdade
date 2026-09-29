public class Pessoa{
    
    public int id;
    public int bufunfa;
    public int bilhete[][];
    public int cont;
    
    public Pessoa(int id, int saldo){
        this.id = id;
        this.bufunfa = saldo;
        this.bilhete = new int[4][50];
        this.cont = 0;
    }
    
}