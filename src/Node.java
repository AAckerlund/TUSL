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
		if(level > next.size())
			return false;
		
		next.set(level, data);
		return true;
	}
	
	public int getHeight()
	{
		return next.size();
	}
	
	public int getData()
	{
		return data;
	}
	
	public boolean setPrev(Node n, int level)
	{
		if(level > prev.size())
			return false;

		prev.set(level, n);
		return true;
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
	
	public void removeElement(int i)
	{
		next.remove(i);
		prev.remove(i);
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

	public void createPointers(int height)
	{
		if(next == null)
		{
			for(int i = 1; i < height; i++)
			{
				next.add(null);
				prev.add(null);
			}
		}
		else
		{
			for(int i = next.size(); i < height; i++)
			{
				next.add(null);
				prev.add(null);
			}
		}
	}
}