public class Driver
{
	SkipList sl;
	
	public Driver()
	{
		sl = new SkipList();
		int[] nums = {6, 7, 3, 2, 4, 1, 9, 0, 5, 8};
		float[] times = new float[nums.length];
		
		for(int i = 0; i < nums.length; i++)//a bunch of inserts
		{
			int pick = nums[i];//(int)(Math.random()*10);
			System.out.println("\ninserting num " + pick);
			times[i] = sl.insertTimed(pick);
			//System.out.println(times[i]);
			sl.print();
		}
		
		System.out.println();
		sl.print();
		System.out.println();
		
		nums = new int[] {0, 6, 4, 6};
		for(int i = 0; i < nums.length; i++)//delete some stuff
		{
			System.out.println("Attempting to remove " + nums[i]);
			if(sl.delete(nums[i]))
				System.out.println(nums[i] + " deleted Successfully!");
			else
				System.out.println(nums[i] + " wasn't in the list!");
			
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
