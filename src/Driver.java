public class Driver
{
	SkipList sl;
	
	public Driver()
	{
		sl = new SkipList();
		int[] nums = {6, 7, 3, 2, 4, 1, 9, 0, 5, 8};
		
		for(int i = 0; i < nums.length; i++)//a bunch of inserts
		{
			int pick = nums[i];//(int)(Math.random()*10);
			System.out.println("\ninserting num " + pick);
			sl.insert(pick);
		}
		
		System.out.println();
		sl.print();
		System.out.println();
		
		if(sl.contains(5))//check if 5 is in the list
		{
			System.out.println("5 is in the data set!\n");
		}
		else
		{
			System.out.println("5 was not found!\n");
		}
		
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
		
		if(sl.contains(5))//check if 5 is in the list
		{
			System.out.println("5 is in the data set!");
		}
		else
		{
			System.out.println("5 was not found!");
		}
	}
	public static void main(String[] args)
	{
		new Driver();
	}
}
