import java.util.ArrayList;

public class Matrix {
	private static final int BORDER_TOP = 1;
	private static final int BORDER_LEFT = 1;

	private int border_bottom;
	private int border_right;
	
	private int middle_line;
	private int middle_column;

    private ArrayList< ItemPos > listItems;	

	public Matrix( int lines, int columns){
		this.border_bottom = BORDER_TOP + lines -1;
		this.border_right = BORDER_LEFT + columns -1;
		
		this.middle_line = BORDER_TOP + (lines -1)/2;
		this.middle_column = BORDER_LEFT + (columns -1)/2;		
		
		this.listItems = new ArrayList< ItemPos > ();		
	}
	
	private class ItemPos {
		private Item i1;
		private int pos_line;
		private int pos_column;
		private int laps;

		public ItemPos( Item i1, int line, int column){
			this.i1 = i1;
			this.pos_line = line;
			this.pos_column = column;
		}
	}
	
	public void addItem( Item i1 ){
		ItemPos ip1 = new ItemPos( i1, 0, 0 );
		this.listItems.add( ip1 );
		i1.setMatrix( this );
	}
	
	public int getQttyLines(){
		return this.border_bottom - BORDER_TOP +1; 
	}

	public int getQttyColumns(){
		return this.border_right - BORDER_LEFT +1; 
	}
	
	private ItemPos find( int id ){
		for( ItemPos ip1 : this.listItems)
			if(ip1.i1.getId() == id)
				return ip1;
		return null;
	}
	
    public int get_line ( int id ) {
		ItemPos ip1 = this.find( id );
		return ip1.pos_line;
    }

    public int get_column ( int id ) {
		ItemPos ip1 = this.find( id );
		return ip1.pos_column;
    }

    public void move ( int id, Move m1 ) {
		ItemPos ip1 = this.find( id );
		
		switch( m1 ) {
			case UP:
				if( ip1.pos_line > BORDER_TOP )
					ip1.pos_line--;
				break;
			case LEFT:
				if( ip1.pos_column > BORDER_LEFT )
					ip1.pos_column--;
				break;
			case DOWN:
				if( ip1.pos_line < this.border_bottom ) {
					ip1.pos_line++;
					if((ip1.pos_line == this.middle_line) && (ip1.pos_column < this.middle_column))
						ip1.laps++;
				}
				break;
			case RIGHT:
				if( ip1.pos_column < this.border_right )
					ip1.pos_column++;			
				break;
		}
    }
}
