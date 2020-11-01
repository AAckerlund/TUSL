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
			origin = tmp;
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
	
	public void doInsert(Node toInsert, Node previous)
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
		System.out.println("height " + (tmpLevel-1) + " reached");
	}
	
	public void delete(int target)
	{
	}
	
	private Node search(int data)
	{
		return null;
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