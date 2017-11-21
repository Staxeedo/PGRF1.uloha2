package cz.uhk.pgrf1.c03.madr.uloha2.sort;

import java.util.ArrayList;
import java.util.List;

public class Sort {
	List<Integer> sortedList;
	
	// algoritmus z algoritmy.net
	   public void sort(List<Integer> list,int alg) {
	        sortedList = list;
	        if(alg==0)
	        quicksort(0, sortedList.size() - 1);
	    }
	
		public  void  quicksort(int lBound, int rBound)
		{
						
			if(lBound < rBound)
			{
				int bound = lBound;
				for(int i = lBound +1;i<rBound;i++)
				{
					if(sortedList.get(i)>sortedList.get(lBound))
					{
						swap(lBound,++bound);
					}
						
						
				}
				swap(lBound,bound);
				quicksort(lBound,bound);
				quicksort(bound+1,rBound);
				
			}

			
		
		}
		
		private  void swap(int lBound,int rBound)
		{
			int tmp = sortedList.get(rBound);
			sortedList.set(rBound,sortedList.get(lBound));
			sortedList.set(lBound,tmp);
		}
		

		public List<Integer> getSortedList()
		{
			return sortedList;
		}

}
