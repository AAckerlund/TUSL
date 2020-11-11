import java.util.concurrent.ConcurrentSkipListSet;
//If you want to look at the built in skip list.
public class test
{
	ConcurrentSkipListSet<Integer> list;
	public test()
	{
		list = new ConcurrentSkipListSet<>();
		for(int i = 0; i < 10; i++)
		{
			list.add((int)(Math.random()*10));
		}
		for(int i = 0; i < list.size(); i++)
		{
			if(list.contains(i))
				System.out.println("found " + i);
		}
	}
	public static void main(String[] args)
	{
		new test();
	}
}
