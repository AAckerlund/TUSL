import java.util.ArrayList;

public class Node
{
	private int data;
	private ArrayList<Node> next;
	private Node prev;//This is required to allow height to not have a predetermined max
	
	public Node(int data)
	{
		this.data = data;
		next = new ArrayList<>();
	}
	
	public Node getNext(int level)
	{
		return next.get(level);
	}
	
	public void setNext(Node data, int level)
	{
		next.add(level, data);
	}
	
	public int getHeight()
	{
		return next.size();
	}
	
	public int getData()
	{
		return data;
	}
	
	public void setPrev(Node n)
	{
		prev = n;
	}
	
	public Node getPrev()
	{
		return prev;
	}
}