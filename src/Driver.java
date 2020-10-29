public class Driver
{
	SkipList sl;
	public Driver()
	{
		sl = new SkipList();
		for(int i = 1; i < 10; i++)
		{
			sl.insert(i);
			System.out.println("inserted num " + i);
		}
		sl.print();
	}
	public static void main(String[] args)
	{
		new Driver();
	}
}
