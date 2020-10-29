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
		if(origin == null)
		{
			Random r = new Random();
			tmp.setNext(tmp, 0);//all nodes must be in the bottom level
			tmp.setPrev(tmp);
			int tmpLevel = 1;
			while(true)
			{
				if(r.nextInt(2) == 1)
				{
					tmp.setNext(tmp, tmpLevel);
				}
				else
				{
					break;
				}
				tmpLevel++;
			}
			origin = tmp;
			return;
		}
		Node curr = origin;
		int level = curr.getHeight()-1;//height list is 0 indexed so subtract 1
		if(curr.getNext(0) == curr)//only 1 node in the list
		{
			Random r = new Random();
			tmp.setNext(curr, 0);//all nodes must be in the bottom level
			curr.setNext(tmp, 0);
			
			tmp.setPrev(curr);
			curr.setPrev(tmp);
			int tmpLevel = 1;
			while(true)
			{
				if(r.nextInt(2) == 1)
				{
					if(tmp.getHeight() > curr.getHeight())
					{
						tmp.setNext(tmp, tmpLevel);
					}
					else
					{
						tmp.setNext(curr.getNext(tmpLevel), tmpLevel);
						curr.setNext(tmp, tmpLevel);
						tmp.setPrev(curr);
					}
				}
				else
				{
					break;
				}
				tmpLevel++;
			}
			return;
		}
		while(curr != null)
		{
			if(curr.getData() == data)//node already exists
				return;
			if(curr.getData() < data && curr.getNext(level).getData() > data)
			{
				if(level == 0)//insert between curr and curr.next
				{
					//determine node height and set all nexts
					Random r = new Random();
					tmp.setNext(curr.getNext(0), 0);//all nodes must be in the bottom level
					curr.setNext(tmp, 0);
					int tmpLevel = 1;
					while(true)
					{
						if(r.nextInt(2) == 1)//50% chance to increase height
						{
							if(tmp.getHeight() > curr.getHeight())
							{
								Node prev = findTallerPrev(curr);
								if(prev == null)//no other node has reached this height
								{
									tmp.setNext(tmp, tmpLevel);
								}
								else
								{
									tmp.setNext(prev.getNext(tmpLevel), tmpLevel);
									prev.setNext(tmp, tmpLevel);
									tmp.setPrev(prev);
								}
							}
							else
							{
								tmp.setNext(curr.getNext(tmpLevel), tmpLevel);
								curr.setNext(tmp, tmpLevel);
								tmp.setPrev(curr);
							}
						}
						else
						{
							break;
						}
						tmpLevel++;
					}
					return;
				}
				else//not on the bottom level, so go down a level and keep looking
				{
					level--;
				}
			}
			else
			{
				if(curr.getNext(level) == null)
				{
					if(level > 0)
					{
						level--;
						curr = curr.getNext(level);
					}
					else//level = 0, we are at the end of the list
					{
						break;
					}
				}
			}
		}
		/*Node tmpNode = new Node(data);
		origin.insert(tmpNode, 0);//all nodes are on the bottom level
		int i = 1;
		while(true)//see if the node gets to a high level
		{
			if(rand.nextDouble() < .5)//insert with prob = .5
				origin.insert(tmpNode, i);
			else
				break;
			i++;
		}*/
	}
	
	public void delete(int target)
	{
		/*System.out.println("Deleting " + target);
		Node victim = search(target);
		if(victim == null)
			return;
		victim.data = 0;
		
		for(int i = 0; i < victim.getHeight(); i++)
		{
			origin.refreshAfterDelete(i);
		}
		System.out.println();*/
	}
	
	private Node search(int data)
	{
		return null;
		/*Node result = origin;
		for(int i = result.getHeight() - 1; i >= 0; i--)//go through the levels (top to bottom) performing a search on each level for the desired data point.
		{
			result = origin.search(data, i);
			if(result != null)
			{
				System.out.println("Found " + data + " at level " + i + ", so stopped\n");
				break;
			}
		}
		
		return result;*/
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
			System.out.println(cur.getData() + " has " + cur.getHeight() + " height");
			cur = cur.getNext(0);
		}while(cur != origin);
	}
}