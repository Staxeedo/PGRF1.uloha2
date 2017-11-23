package cz.uhk.pgrf1.c03.madr.uloha2.sort;

import java.util.List;
/**
 * 
 * Trida pro sortovani pole
 *
 */
public class Sort {
	List<Integer> sortedList;
	
	
	   public void sort(List<Integer> list,int alg) {
	        sortedList = list;
	        if(alg==0)
	        insertSort();
	   }
		public  void  insertSort()
		{
			for (int i = 0; i < sortedList.size() - 1; i++) {
			
				int j = i+1;
				int temp = sortedList.get(j);
				while(j>0&&temp>sortedList.get(j-1))
				{
					sortedList.set(j, sortedList.get(j-1));
					j--;
				}
				sortedList.set(j, temp);
			}
						
		
		}
		
		public List<Integer> getSortedList()
		{
			return sortedList;
		}

}
