import java.util.Random;

public class SkipList
{
	private Node origin;
	
	public SkipList()
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
		Random r = new Random();
		//determine node height and set all nexts
		toInsert.setNext(previous.getNext(0), 0);//all nodes must be in the bottom level
		toInsert.setPrev(previous);
		
		previous.getNext(0).setPrev(toInsert);
		previous.setNext(toInsert, 0);
		
		int tmpLevel = 1;
		while(true)
		{
			if(r.nextInt(2) == 1)//50% chance to increase height
			{
				if(previous == null)
				{
					toInsert.setNext(toInsert, tmpLevel);
				}
				else if(toInsert.getHeight() >= previous.getHeight())
				{
					previous = findTallerPrev(previous);
					if(previous == null)//no other node has reached this height
					{
						toInsert.setNext(toInsert, tmpLevel);
					}
					else
					{
						toInsert.setNext(previous.getNext(tmpLevel), tmpLevel);
						previous.setNext(toInsert, tmpLevel);
					}
				}
				else
				{
					toInsert.setNext(previous.getNext(tmpLevel), tmpLevel);
					previous.setNext(toInsert, tmpLevel);
				}
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
		System.out.println("height " + (tmpLevel-1) + " reached");
	}
	
	public boolean delete(int target)
	{
		return search(target, 2) != null;
	}
	
	private int doDelete(Node previous, Node toDelete, int height)
	{
		do
		{
			previous.setNext(toDelete.getNext(height), height);
			height--;
		}while(previous.getNext(height) == toDelete && height >= 0);
		height++;
		if(height == 0)
		{
			toDelete.getNext(0).setPrev(previous);
			if(origin == toDelete)//change the origin node if it would be deleted
				origin = previous.getNext(0);
		}
		return height;
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
					return curr;
				else if(op == 1)
					return null;
				else if(op == 2)
				{
					doDelete(findTallerPrev(curr), curr, level);
					return curr;
				}
			}
			if(op == 2)
			{
				if(curr.getNext(level).getData() == data)//node already exists
				{
					level = doDelete(curr, curr.getNext(level), level);
					if(level == 0)
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
						return null;
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
						doInsert(new Node(data), curr);
					return null;
				}
			}
			else
			{
				while(curr == curr.getNext(level))//no point in checking the same node multiple times so drop down levels until node.next() gives a different node.
				{
					level--;
				}
				curr = curr.getNext(level);
			}
		}
		//return null;//no such element exists
	}
	
	public boolean contains(int data)
	{
		return search(data, 0) != null;
	}
	
	public Node findTallerPrev(Node start)
	{
		int height = start.getHeight();
		Node prev = start.getPrev();
		while(true)
		{
			if(prev.getHeight() >= height)
				return prev;
			else if(prev == start)//we have checked everything
				return null;
			prev = prev.getPrev();
		}
	}
	
	public void print()
	{
		Node cur = origin;
		do
		{
			System.out.println(cur.getData() + " reaches " + (cur.getHeight()-1) + " height");
			cur = cur.getNext(0);
		}while(cur != origin);
	}
}