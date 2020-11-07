import java.util.Random;

public class SkipList
{
	private Node origin;
	
	public SkipList()
	{
		origin = null;
	}
	
	public void insert(int data)
	{
		Node tmp = new Node(data);
		if(origin == null)//the skip list is empty
		{
			doInsert(tmp, tmp);
			return;
		}
		Node curr = origin;
		int level = curr.getHeight()-1;//height list is 0 indexed so subtract 1
		while(true)//iterate through the list and search for where to insert
		{
			if(curr.getData() == data)//node already exists
			{
				System.out.println(data + " already exists");
				return;
			}
			if(curr.getNext(level) == null)
			{
				level--;
			}
			else if(curr.getData() < data && curr.getNext(level).getData() > data)//data is between curr and curr.getNext()
			{
				if(level == 0)//insert between curr and curr.next
				{
					doInsert(tmp, curr);
					return;
				}
				else//not on the bottom level, so go down a level and keep looking
				{
					level--;
				}
			}
			else if((curr.getData() < data || curr.getNext(level).getData() > data) && curr.getNext(level).getData() <= curr.getData())//data is greater than greatest node or less than smallest node and curr.getNext() loops back to the start of the list
			{
				if(level > 0)
				{
					level--;
				}
				else//level = 0
				{
					doInsert(tmp, curr);
					return;
				}
			}
			else
			{
				while(curr == curr.getNext(level))
				{
					level--;
				}
				curr = curr.getNext(level);
			}
		}
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
		if(origin == null || !contains(target))//the skip list is empty
		{
			return false;
		}
		Node curr = origin;
		int level = curr.getHeight()-1;//height list is 0 indexed so subtract 1
		while(true)//iterate through the list and search for where to insert
		{
			//System.out.println(curr.getNext(level));
			if(curr.getNext(level).getData() == target)//node already exists
			{
				level = doDelete(curr, curr.getNext(level), level);
				if(level == 0)
					return true;
			}
			if(curr.getNext(level) == null)
			{
				level--;
			}
			else if(curr.getData() < target && curr.getNext(level).getData() > target)//data is between curr and curr.getNext()
			{
				if(level >= 0)//insert between curr and curr.next
				{
					level--;
				}
			}
			else if((curr.getData() < target || curr.getNext(level).getData() > target) && curr.getNext(level).getData() <= curr.getData())//data is greater than greatest node or less than smallest node and curr.getNext() loops back to the start of the list
			{
				if(level > 0)
				{
					level--;
				}
			}
			else
			{
				while(curr == curr.getNext(level))
				{
					level--;
				}
				curr = curr.getNext(level);
			}
		}
	}
	
	private int doDelete(Node previous, Node toDelete, int height)
	{
		do
		{
			previous.setNext(toDelete.getNext(height), height);
			height--;
		}while(previous.getNext(height) == toDelete || height >= 0);
		height++;
		if(height == 0)
		{
			toDelete.getNext(0).setPrev(previous);
			if(origin == toDelete)//change the origin node if it would be deleted
				origin = previous.getNext(0);
		}
		return height;
	}
	
	/*
	op = 0 - searching for a node
	op = 1 - inserting a node
	op = 2 - deleting a node
	 */
	public Node search(int data, int op)
	{
		if(origin == null)//the skip list is empty
		{
			if(op == 1)
				doInsert(new Node(data), new Node(data));
			return null;
		}
		Node curr = origin;
		int level = curr.getHeight()-1;//height list is 0 indexed so subtract 1
		do//iterate through the list and search for where to insert
		{
			if(curr.getData() == data)//found node
			{
				if(op == 1)
					return null;
				else if(op == 2)
					return curr;
			}
			if(curr.getNext(level) == null)//nothing else on this height, so drop down a level and keep checking
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
		}while(curr != origin);
		return null;//no such element exists
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
			if(prev.getHeight() > height)
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