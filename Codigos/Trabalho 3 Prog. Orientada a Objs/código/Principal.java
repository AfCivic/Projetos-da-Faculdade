public class Principal {
    public static void main (String args[]) {
		
        Matrix mat1 = new Matrix(10,10);
        Robot r1 = new Robot();
        
        mat1.addItem( r1 );
        
        System.out.print("\nInício\n");
        while( true ) {
            Move mov1 = r1.move();
            if( mov1 == Move.STOP )
               break;
            mat1.move( r1.getId(), mov1 );
        }
        System.out.print("\nFim\n");
    }
}
