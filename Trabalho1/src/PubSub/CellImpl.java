package PubSub;

public class CellImpl implements Cell {
	String message = "";
	String name = "";

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}


	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public void setName(String name) {
		this.name = name;
		
	}
	
	

}
