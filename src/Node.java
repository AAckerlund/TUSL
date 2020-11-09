import java.util.ArrayList;

public class Node
{
	private int data;
	private ArrayList<Node> next;
	private ArrayList<Node> prev;//Makes many operations easier, the list is never traversed backwards
	
	public Node(int data)
	{
		this.data = data;
		next = new ArrayList<>();
		next.add(this);
		prev = new ArrayList<>();
		prev.add(this);
	}
	
	public Node getNext(int level)
	{
		try
		{
			return next.get(level);
		}
		catch(IndexOutOfBoundsException ex)
		{
			return null;
		}
	}
	
	public void setNext(Node data, int level)
	{
		if(next.size() == level)
		{
			next.add(level, data);
		}
		else
		{
			next.set(level, data);
		}
	}
	
	public int getHeight()
	{
		return next.size();
	}
	
	public int getData()
	{
		return data;
	}
	
	public void setPrev(Node n, int level)
	{
		if(prev.size() == level)
		{
			prev.add(level, n);
		}
		else
		{
			prev.set(level, n);
		}
	}
	
	public Node getPrev(int level)
	{
		try
		{
			return prev.get(level);
		}
		catch(IndexOutOfBoundsException ex)
		{
			return null;
		}
	}
	
	public void removeNext()
	{
		next.remove(next.size()-1);
		prev.remove(prev.size()-1);
	}
}