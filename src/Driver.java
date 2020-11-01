public class Driver
{
	SkipList sl;
	public Driver()
	{
		sl = new SkipList();
		int[] nums = {5, 8, 3};
		for(int i = 1; i < 10; i++)
		{
			int pick = (int)(Math.random()*10);
			System.out.println("inserting num " + pick);
			sl.insert(pick);
			System.out.println("inserted num " + pick);
		}
		sl.print();
	}
	public static void main(String[] args)
	{
		new Driver();
	}
}
