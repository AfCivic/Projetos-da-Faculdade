public abstract class Item {
	private static int qtty = 0;

	protected int id;
	protected int pos_line;
	protected int pos_column;
	protected Matrix mat1;
	
	public Item(){
		qtty++;
		this.id = qtty;
	}
	
	public void setMatrix( Matrix mat1 ){
		this.mat1 = mat1;
	}
	
	public int getId() {
		return this.id;
	}
}

