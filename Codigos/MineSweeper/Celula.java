public class Celula{
   private boolean mina;

   public Celula(){
    this.mina = false;
   }

   public void setmina (){
    this.mina = true;
   }
   public boolean perdeu (){
      return this.mina;
   }

}
