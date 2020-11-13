import java.util.Random;

public class TUSL
{
	private Node origin;
	public int steps;
	
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
		//System.out.println("cur " + toInsert.getData() + ", prev " + previous.getData());
		//determine node height and set all nexts
		int level = toInsert.determineHeight();
		toInsert.createPointers(level);

		toInsert.setNext(previous.getNext(0), 0);//all nodes must be in the bottom level
		toInsert.setPrev(previous, 0);
		
		previous.getNext(0).setPrev(toInsert, 0);
		previous.setNext(toInsert, 0);
		for(int i = 1; i < level; i++)
		{
			if(previous.getNext(i) == null)
			{
				Node firstPrev = previous;
				do
				{
					previous = previous.getPrev(i - 1);
				}
				while(previous.getNext(i) == null && previous != firstPrev);

				if(previous == firstPrev)//no other node has reached this height
				{
					previous = toInsert;
				}
			}
			if(previous.getNext(i) == null)
			{
				toInsert.setNext(toInsert, i);
				toInsert.setPrev(toInsert, i);
			}
			else
			{
				toInsert.setNext(previous.getNext(i), i);
				toInsert.setPrev(previous, i);

				previous.getNext(i).setPrev(toInsert, i);

				previous.setNext(toInsert, i);
			}
		}
		if(origin == null || toInsert.getData() < origin.getData())
		{
			origin = toInsert;
		}
		newOrigin();
		//System.out.println("height " + (level+1) + " reached");
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
		origin = heightAdjust(origin);
	}
	
	/* op codes
	op = 0 - searching for a node
	op = 1 - inserting a node
	op = 2 - deleting a node
	 */
	public Node search(int data, int op)
	{
		steps = 0;
		if(origin == null)//the skip list is empty
		{
			steps += 1;
			if(op == 1)
			{
				steps += 1;
				Node tmp = new Node(data);
				steps += 1;
				doInsert(tmp, tmp);
				steps += 1;
				return null;
			}
			return null;
		}
		Node curr = origin;
		steps += 1;
		int level = curr.getHeight()-1;//height list is 0 indexed so subtract 1
		steps += 1;
		while(true)//iterate through the list and search for where to insert
		{
			steps += 1;
			if(curr.getData() == data)//found node
			{
				steps += 1;
				if(op == 0)
				{
					steps += 1;
					newOrigin();
					steps += 1;
					curr = heightAdjust(origin);
					steps += 1;
					return curr;
				}
				else if(op == 1)
				{
					steps += 1;
					newOrigin();
					steps += 1;
					curr = heightAdjust(origin, curr.getHeight());
					steps += 1;
					return null;
				}
				else if(op == 2)
				{
					steps += 1;
					doDelete(curr.getPrev(level), curr, level);
					steps += 1;
					return curr;
				}
			}
			else if(curr.getNext(level) == null)//nothing else on this height, so drop down a level and keep checking
			{
				steps += 1;
				level--;
				steps += 1;
			}
			else if(curr.getData() < data && curr.getNext(level).getData() > data)//data is between curr and curr.getNext()
			{
				steps += 1;
				if(level > 0) {//try and drop down a level, if we can't return null
					steps += 1;
					level--;
					steps += 1;
				}
				else//level == 0
				{
					steps += 1;
					if(op == 1)
					{
						steps += 1;
						doInsert(new Node(data), curr);
						steps += 1;
					}
					return null;
				}
			}
			else if((curr.getData() < data || curr.getNext(level).getData() > data) && curr.getNext(level).getData() <= curr.getData())//data is greater than greatest node or less than smallest node and curr.getNext() loops back to the start of the list
			{
				steps += 1;
				if(level > 0) {
					steps += 1;
					level--;
					steps += 1;
				}
				else//level == 0
				{
					steps += 1;
					if(op == 1)
					{
						steps += 1;
						doInsert(new Node(data), curr);
						steps += 1;
					}
					return null;
				}
			}
			else
			{
				steps += 1;
				while(curr == curr.getNext(level)) {//no point in checking the same node multiple times so drop down levels until node.next() gives a different node.
					steps += 1;
					level--;
					steps += 1;
				}
				curr = curr.getNext(level);
				steps += 1;
			}
		}
	}
	
	public boolean contains(int data)
	{
		return search(data, 0) != null;
	}
	
	public Node heightAdjust(Node n)
	{
		int height = 1;
		steps += 1;
		Random r = new Random();
		steps += 1;
		while(r.nextInt(2) == 1) {
			steps += 1;
			height++;
			steps += 1;
		}
		return heightAdjust(n, height);
	}
	
	public Node heightAdjust(Node n, int height)
	{
		if(n.getHeight() == height)
		{
			steps += 1;
			return n;
		}
		if(n.getHeight() > height)
		{
			steps += 1;
			for(int i = n.getHeight()-1; i >= height; i--)
			{
				steps += 1;
				if(n == n.getNext(i))
				{
					steps += 1;
					n.setPrev(null, i);
					steps += 1;
					n.setNext(null, i);
					steps += 1;
				}
				else
				{
					steps += 1;
					n.getPrev(i).setNext(n.getNext(i), i);
					steps += 1;
					n.getNext(i).setPrev(n.getPrev(i), i);
					steps += 1;
				}
				n.setNext(null, i);
				steps += 1;
				n.setPrev(null, i);
				steps += 1;
				n.removeElement(i);
				steps += 1;
			}
		}
		else
		{
			steps += 1;
			int oldHeight = n.getHeight();
			steps += 1;
			n.createPointers(height);
			steps += 1;
			for(int i = oldHeight; i < height; i++)
			{
				steps += 1;
				Node previous = n.getPrev(i-1);
				steps += 1;
				if(previous.getNext(i) == null)
				{
					steps += 1;
					Node firstPrev = previous;
					steps += 1;
					do
					{
						previous = previous.getPrev(i - 1);
						steps += 1;
					}
					while(previous.getNext(i) == null && previous != firstPrev);
					steps += 1;
					if(previous == firstPrev)//no other node has reached this height
					{
						steps += 1;
						previous = n;
						steps += 1;
					}
				}
				if(previous.getNext(i) == null)
				{
					steps += 1;
					n.setNext(n, i);
					steps += 1;
					n.setPrev(n, i);
					steps += 1;
				}
				else
				{
					steps += 1;
					n.setNext(previous.getNext(i), i);
					steps += 1;
					n.setPrev(previous, i);
					steps += 1;

					previous.getNext(i).setPrev(n, i);
					steps += 1;

					previous.setNext(n, i);
					steps += 1;
				}
			}
		}
		if(origin == null)
		{
			steps += 1;
			origin = n;
			steps += 1;
		}
		return n;
	}
	
	public void newOrigin()
	{
		Random r = new Random();
		steps += 1;
		while(r.nextInt(10) < 9)//90% chance to move to a new origin node
		{
			steps += 1;
			origin = origin.getNext(0);
			steps += 1;
		}
	}
	
	public void print()
	{
		Node cur = origin;
		do
		{
			System.out.print(cur.getData() + ": ");
			for(int i = 0; i < cur.getHeight(); i++)
			{
				try
				{
					System.out.print(cur.getNext(i).getData() + " ");
				}
				catch(NullPointerException ignored){
					System.out.print("n ");
				}
			}
			System.out.println();
			cur = cur.getNext(0);
		}while(cur != origin);
	}
}