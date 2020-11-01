public class Driver
{
	SkipList sl;
	public Driver()
	{
		sl = new SkipList();
		int[] nums = {6, 7, 3};
		for(int i = 0; i < 10; i++)
		{
			int pick = (int)(Math.random()*10);
			System.out.println("\ninserting num " + pick);
			sl.insert(pick);
			
			System.out.println();
			sl.print();
			System.out.println();
		}
	}
	public static void main(String[] args)
	{
		new Driver();
	}
}
