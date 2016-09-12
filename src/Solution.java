import java.io.*;
import java.util.*;

import java.util.Scanner;
import java.util.Comparator;

public class Solution {

	private static class NodeRelationItem{
		int firstNodeId;
		int secondNodeId;
		int distance;
		
		public NodeRelationItem(int firstID, int secondID, int distance){
			if(firstID < secondID)
			{
				this.firstNodeId = firstID;
				this.secondNodeId = secondID;
			}
			else
			{
				this.firstNodeId = secondID;
				this.secondNodeId = firstID;
			}
			this.distance = distance;
		}
		
		public int getDistance() {return distance;} 
		
		public String toString()
		{
			return "<NodeRelationItem>(firstID = " + firstNodeId + ", secondID = " + secondNodeId + ", distance " + distance + ")"; 
		}
	}

	private static class NodeRelationItemComparator implements Comparator<NodeRelationItem>
	{
	    @Override
	    public int compare(NodeRelationItem x, NodeRelationItem y)
	    {
	        // Assume neither string is null. Real code should
	        // probably be more robust
	        // You could also just return x.length() - y.length(),
	        // which would be more efficient.
	        if (x.getDistance() < y.getDistance())
	        {
	            return -1;
	        }
	        if (x.getDistance() > y.getDistance())
	        {
	            return 1;
	        }
	        return 0;
	    }
	}
	
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int cities;
        int roads;
    	
        System.out.println("Please enter the first line: ");
        //cities = input.nextInt();
        //roads = input.nextInt();
        
        //System.out.println("We face a case with " + cities + " cities and " + roads + " roads.");
        
        Comparator<NodeRelationItem> comparator = new NodeRelationItemComparator();
        PriorityQueue<NodeRelationItem> queue = new PriorityQueue<NodeRelationItem>(10, comparator);
        queue.add(new NodeRelationItem(4,6,1));
        queue.add(new NodeRelationItem(4,5,3));
        queue.add(new NodeRelationItem(4,7,2));
        queue.add(new NodeRelationItem(4,7,2));
        queue.add(new NodeRelationItem(4,7,2));
        queue.add(new NodeRelationItem(4,7,2));
        
        NodeRelationItem NR;
        while((NR = queue.poll()) != null)
        {
        	System.out.println(NR);
        	while(queue.contains(NR)) queue.remove(NR);
        }
        
        System.exit(1);
        
        int data;
        while(input.hasNextInt())
        {
        	data = input.nextInt();
            System.out.println(data);
        }
        System.out.println("Finished data parsing");
    	
    }
}