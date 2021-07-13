package test;
import java.io.Serializable;

public class Rule implements Serializable {
	public int low;
	public int high;
	public String name;

	public Rule(int low, int high, String name)
	{
		this.low = low;
		this.high = high;
		this.name = name;
	}

	@Override
	public String toString(){
			return "name="+name;
	}
}
