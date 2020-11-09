import java.util.Random;

public class TUSL
{
	private Node origin;
	
	public TUSL()
	{
		origin = null;
	}
	
	public float insertTimed(int data)
	{
		float delta = System.nanoTime();
		insert(data);
		delta = System.nanoTime() - delta;
		return delta;
	}
	
	public float deleteTimed(int data)
	{
		float delta = System.nanoTime();
		delete(data);
		delta = System.nanoTime() - delta;
		return delta;
	}
	
	public float searchTimed(int data)
	{
		float delta = System.nanoTime();
		search(data, 0);
		delta = System.nanoTime() - delta;
		return delta;
	}
	
	public void insert(int data)
	{
		search(data, 1);
	}
	
	private void doInsert(Node toInsert, Node previous)
	{
		System.out.println(toInsert.getData() + ", " + previous.getData());
		Random r = new Random();
		//determine node height and set all nexts
		toInsert.setNext(previous.getNext(0), 0);//all nodes must be in the bottom level
		toInsert.setPrev(previous, 0);
		
		previous.getNext(0).setPrev(toInsert, 0);
		previous.setNext(toInsert, 0);
		
		int tmpLevel = 1;
		while(true)
		{
			if(r.nextInt(2) == 1)//50% chance to increase height
			{
				if(toInsert.getData() == 6)
					break;
				if(toInsert.getHeight() >= previous.getHeight())
				{
					Node firstPrev = previous;
					do
					{
						previous = previous.getPrev(tmpLevel-1);
					}while(toInsert.getHeight() >= previous.getHeight() && previous != firstPrev);
					if(previous == firstPrev)//nothing taller was found so link the node to itself
						previous = toInsert;
					
				}
				toInsert.setNext(previous.getNext(tmpLevel), tmpLevel);
				toInsert.setPrev(previous, tmpLevel);
				
				previous.setNext(toInsert, tmpLevel);
				
				previous.getNext(tmpLevel).setPrev(toInsert, tmpLevel);
			}
			else
			{
				break;
			}
			tmpLevel++;
		}
		if(origin == null)
		{
			origin = toInsert;
		}
		else if(toInsert.getData() < origin.getData())
		{
			origin = toInsert;
		}
		newOrigin();
		//System.out.println("height " + (tmpLevel-1) + " reached");
	}
	
	public boolean delete(int target)
	{
		return search(target, 2) != null;
	}
	
	private void doDelete(Node previous, Node toDelete, int height)
	{
		do
		{
			previous.setNext(toDelete.getNext(height), height);
			toDelete.getNext(height).setPrev(previous, height);
			height--;
		}while(previous.getNext(height) == toDelete && height >= 0);
		height++;
		if(height == 0)
		{
			toDelete.getNext(0).setPrev(previous, 0);
			previous.setNext(toDelete.getNext(0), 0);
			if(origin == toDelete)//change the origin node if it would be deleted
				origin = previous.getNext(0);
		}
		newOrigin();
		heightAdjust(origin);
	}
	
	/* op codes
	op = 0 - searching for a node
	op = 1 - inserting a node
	op = 2 - deleting a node
	 */
	public Node search(int data, int op)
	{
		if(origin == null)//the skip list is empty
		{
			if(op == 1)
			{
				Node tmp = new Node(data);
				doInsert(tmp, tmp);
				return null;
			}
			return null;
		}
		Node curr = origin;
		int level = curr.getHeight()-1;//height list is 0 indexed so subtract 1
		while(true)//iterate through the list and search for where to insert
		{
			if(curr.getData() == data)//found node
			{
				if(op == 0)
				{
					newOrigin();
					heightAdjust(origin);
					return curr;
				}
				else if(op == 1)
				{
					newOrigin();
					heightAdjust(origin, curr.getHeight());
					return null;
				}
				else if(op == 2)
				{
					doDelete(curr.getPrev(level), curr, level);
					return curr;
				}
			}
			else if(curr.getNext(level) == null)//nothing else on this height, so drop down a level and keep checking
			{
				level--;
			}
			else if(curr.getData() < data && curr.getNext(level).getData() > data)//data is between curr and curr.getNext()
			{
				if(level > 0)//try and drop down a level, if we can't return null
					level--;
				else//level == 0
				{
					if(op == 1)
					{
						doInsert(new Node(data), curr);
					}
					return null;
				}
			}
			else if((curr.getData() < data || curr.getNext(level).getData() > data) && curr.getNext(level).getData() <= curr.getData())//data is greater than greatest node or less than smallest node and curr.getNext() loops back to the start of the list
			{
				if(level > 0)
					level--;
				else//level == 0
				{
					if(op == 1)
					{
						doInsert(new Node(data), curr);
					}
					return null;
				}
			}
			else
			{
				while(curr == curr.getNext(level))//no point in checking the same node multiple times so drop down levels until node.next() gives a different node.
					level--;
				curr = curr.getNext(level);
			}
		}
	}
	
	public boolean contains(int data)
	{
		return search(data, 0) != null;
	}
	
	public void print()
	{
		Node cur = origin;
		do
		{
			System.out.println(cur.getData() + " points to " + cur.getHeight() + " elements");
			for(int i = 0; i < cur.getHeight(); i++)
			{
				try
				{
					System.out.println("\t" + cur.getNext(i).getData() + " at height " + i);
				}
				catch(NullPointerException ignored){}
			}
			//System.out.println(cur.getData() + " reaches " + (cur.getHeight()-1) + " height");
			System.out.println();
			cur = cur.getNext(0);
		}while(cur != origin);
	}
	
	public void heightAdjust(Node n)
	{
		int newHeight = 1;
		Random r = new Random();
		while(true)
		{
			if(r.nextInt(2) == 1)//50% chance to increase height
			{
				if(newHeight >= n.getHeight())
				{
					Node previous = n.getPrev(newHeight-1);
					while(previous.getHeight() < newHeight && previous != n)//find a taller previous node
					{
						previous.getPrev(newHeight-1);
					}
					
					n.setPrev(previous, newHeight);
					previous.getNext(newHeight).setPrev(n, newHeight);
					
					Node next = n.getNext(newHeight-1);
					while(next.getHeight() < newHeight && next != n)
					{
						next = next.getNext(newHeight-1);
					}
					n.setNext(next, newHeight);
					previous.setNext(n, newHeight);
				}
			}
			else
			{
				break;
			}
			newHeight++;
		}
		if(origin == null)
		{
			origin = n;
		}
	}
	
	public void heightAdjust(Node n, int height)
	{
		if(n.getHeight() > height)
		{
			for(int i = n.getHeight()-1; i > height; i--)
			{
				n.getPrev(i).setNext(n.getNext(i), i);
				n.getNext(i).setPrev(n.getPrev(i), i);
				
				n.removeNext();
			}
		}
		else
		{
			for(int i = 0; i < height; i++)
			{
				if(i >= n.getHeight())
				{
					Node previous = n.getPrev(i - 1);
					while(previous.getHeight() < i && previous != n)//find a taller previous node
					{
						previous.getPrev(i - 1);
					}
					
					n.setPrev(previous, i);
					previous.getNext(i).setPrev(n, i);
					
					Node next = n.getNext(i - 1);
					while(next.getHeight() < i && next != n)
					{
						next = next.getNext(i - 1);
					}
					n.setNext(next, i);
					previous.setNext(n, i);
				}
				else
				{
					break;
				}
			}
		}
		if(origin == null)
		{
			origin = n;
		}
	}
	
	public void newOrigin()
	{
		Random r = new Random();
		while(r.nextInt(10) < 9)//90% chance to move to a new origin node
		{
			origin = origin.getNext(0);
		}
	}
}