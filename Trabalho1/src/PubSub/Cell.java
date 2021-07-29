package PubSub;

import java.io.Serializable;


public interface Cell extends Serializable{
	public String getMessage();
	public void setMessage(String name);
	public String getName();
	public void setName(String name);
}
