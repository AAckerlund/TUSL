public class Driver
{
	TUSL sl;
	
	public Driver()
	{
		sl = new TUSL();
		int[] nums = {6, 7, 3, 2, 4, 1, 9, 0, 5, 8};
		
		for(int i = 0; i < nums.length; i++)//a bunch of inserts
		{
			int pick = nums[i];
			System.out.println("\ninserting num " + pick);
			sl.insert(pick);
			sl.print();
		}
		
		/*System.out.println("--------------------------DELETING--------------------------");
		
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
		}*/
		
	}
	public static void main(String[] args)
	{
		new Driver();
	}
}
