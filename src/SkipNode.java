import java.util.ArrayList;

public class SkipNode
{
	
	int data;
	public SkipNode[] next;
	
	public SkipNode(int data)
	{
		next = new SkipNode[5];//allows the node to have a next() for each height
		this.data = data;
	}
	
	public SkipNode search(int data, int level)
	{
		System.out.print("Searching for: " + data + " at ");
		print(level);
		
		SkipNode result = null;
		SkipNode current = getNext(level);
		while(current != null && current.data <= data)
		{
			if(current.data == data)
			{
				result = current;
				break;
			}
			
			current = current.getNext(level);
		}
		
		return result;
	}
	
	public void insert(SkipNode newNode, int level)
	{
		SkipNode current = getNext(level);
		if(current == null)//We have reached new heights!
		{
			setNext(newNode, level);
			return;
		}
		else if(newNode.data <= current.data)
		{
			setNext(newNode, level);
			newNode.setNext(current, level);
			return;
		}
		/*
		while(current.getNext(level) != null && current.data <= SkipNode.data && current.getNext(level).data <= SkipNode.data)
		{
			current = current.getNext(level);
		}
		SkipNode successor = current.getNext(level);
		current.setNext(SkipNode, level);
		SkipNode.setNext(successor, level);
		System.out.println("inserted " + SkipNode.data + " on level " + level);*/
	}
	
	public void print(int level)
	{
		System.out.print("level " + level + ": [");
		int length = 0;
		SkipNode current = getNext(level);
		while(current != null)
		{
			length++;
			System.out.print(current.data + " ");
			current = current.getNext(level);
		}
		
		System.out.println("], length: " + length);
	}
	
	public void refreshAfterDelete(int level)
	{
		SkipNode current = getNext(level);
		while(current != null && current.getNext(level) != null)//TODO change to "do while" not at first node
		{
			if(current.getNext(level).data == 0)
			{
				SkipNode successor = current.getNext(level).getNext(level);
				current.setNext(successor, level);
				return;
			}
			
			current = current.getNext(level);
		}
	}
	
	public void setNext(SkipNode nextNode, int level)//sets the data points at the specified level
	{
		next[level] = nextNode;
	}
	
	public SkipNode getNext(int level)//returns the data points at the specified level
	{
		return next[level];
	}
}