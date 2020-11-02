public class Driver
{
	SkipList sl;
	public Driver()
	{
		sl = new SkipList();
		int[] nums = {6, 7, 3, 2, 4, 1, 9, 0, 5, 8};
		for(int i = 0; i < nums.length; i++)
		{
			int pick = nums[i];//(int)(Math.random()*10);
			System.out.println("\ninserting num " + pick);
			sl.insert(pick);
			
			System.out.println();
			sl.print();
			System.out.println();
		}
		System.out.println(sl.contains(5));
	}
	public static void main(String[] args)
	{
		new Driver();
	}
}
