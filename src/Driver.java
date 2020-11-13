import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;
import java.util.Random;

public class Driver
{
	SkipList sl;
	
	public Driver()
	{
		sl = new SkipList();
		int[] nums = {6, 7, 3, 2, 4, 1, 9, 0, 5, 8, 8};

		ArrayList<Integer> input = new ArrayList<>();
		Random random = new Random();
		for(int i = 0; i<100; i++){
			int num = random.nextInt(100);
			input.add(num);
		}

		System.out.println("--------------------------INSERTS--------------------------");
		/*for(int i = 0; i < nums.length; i++)//a bunch of inserts
		{
			int pick = nums[i];
			System.out.println("\ninserting num " + pick);
			sl.insert(pick);
			System.out.println("STEPS: " + sl.steps);
			sl.print();
		}*/

		System.out.println("COMPARING SKIPLIST AND TUSL INSERTS");
		SkipList skiplist = new SkipList();
		TUSL tusl = new TUSL();

		int skipListInsertStepTotal = 0;
		int TUSLInsertStepTotal = 0;
		long totalSLtime = 0;
		long totalTUSLtime = 0;
		int iterations = 100000;
		for(int j = 0; j<iterations; j++) {
			for (int i = 0; i < input.size(); i++)//a bunch of inserts
			{
				int pick = input.get(i);
				//System.out.println("\ninserting num " + pick);

				float SLtime = skiplist.insertTimed(pick);
				float Ttime = tusl.insertTimed(pick);

				totalSLtime += SLtime;
				totalTUSLtime += Ttime;

				skipListInsertStepTotal += skiplist.steps;
				TUSLInsertStepTotal += tusl.steps;
			}
		}
		System.out.println("SKIPLIST INSERT AVERAGE RUNTIME STEPS: " + skipListInsertStepTotal/iterations);
		System.out.println("TUSL INSERT AVERAGE RUNTIME STEPS: " + TUSLInsertStepTotal/iterations);
		System.out.println("SKIPLIST INSERT AVERAGE RUNTIME NANOSECONDS " + totalSLtime);
		System.out.println("TUSL INSERT AVERAGE RUNTIME NANOSECONDS " + totalTUSLtime);

		System.out.println("--------------------------CONTAINS--------------------------");
		System.out.println();
		System.out.println(sl.contains(5));
		System.out.println();
		System.out.println(sl.contains(12));
		System.out.println();
		sl.print();

		System.out.println("--------------------------DELETING--------------------------");
		
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
