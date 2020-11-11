import java.util.ArrayList;
import java.util.Random;

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
	
	public boolean setNext(Node data, int level)
	{
		if(level >= next.size())
			return false;
		
		next.set(level, data);
		return false;
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
	
	public int determineHeight()
	{
		int height = 1;
		Random r = new Random();
		while(true)
		{
			if(r.nextInt(2) == 1)//50% chance to increase height
			{
				height++;
			}
			else
				return height;
		}
	}
}