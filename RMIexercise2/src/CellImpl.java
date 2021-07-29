import java.rmi.RemoteException;

public class CellImpl implements Cell{
	int contents = 0;
	
	@Override
	public void set(int v){
		contents = v;

	}

	@Override
	public int get(){
		return contents;
	}
}
