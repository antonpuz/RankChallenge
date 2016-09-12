import java.io.*;
import java.util.*;

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
		
		public int getfirstNodeId() {return firstNodeId;} 
		public int getsecondNodeId() {return secondNodeId;} 
		public int getDistance() {return distance;} 
		
		public String toString()
		{
			return "<NodeRelationItem>(firstID = " + firstNodeId + ", secondID = " + secondNodeId + ", distance " + distance + ")"; 
		}
		
		@Override
		public boolean equals(Object other){
			NodeRelationItem otherItem = (NodeRelationItem)other;
			if(this.firstNodeId == otherItem.firstNodeId &&
					this.secondNodeId == otherItem.secondNodeId &&
					this.distance == otherItem.distance) return true;
			
			return false;
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
    	
        //System.out.println("Please enter the first line: ");
        cities = input.nextInt();
        roads = input.nextInt();
        
        //System.out.println("We face a case with " + cities + " cities and " + roads + " roads.");
        
        HashMap<Integer, ArrayList<NodeRelationItem>> neightboring = new HashMap<>();
        
        Comparator<NodeRelationItem> comparator = new NodeRelationItemComparator();
        PriorityQueue<NodeRelationItem> queue = new PriorityQueue<NodeRelationItem>(roads, comparator);
        
        /*
        while(queue.contains(new NodeRelationItem(4,7,2))) queue.remove(new NodeRelationItem(4,7,2));
        
        NodeRelationItem NR;
        while((NR = queue.poll()) != null)
        {
        	System.out.println(NR);
        	while(queue.contains(NR)) queue.remove(NR);
        }
        
        System.exit(1);
        */
        
        int city1;
        int city2;
        int cityDistance;
        for(int i=0; i< roads; i++)
        {
        	city1 = input.nextInt();
        	city2 = input.nextInt();
        	cityDistance = input.nextInt();
        	
        	//Update distances map
        	ArrayList<NodeRelationItem> data1 = neightboring.getOrDefault(city1, new ArrayList<NodeRelationItem>());
        	data1.add(new NodeRelationItem(city1,city2,cityDistance));
        	neightboring.put(city1, data1);
        	
        	ArrayList<NodeRelationItem> data2 = neightboring.getOrDefault(city2, new ArrayList<NodeRelationItem>());
        	data2.add(new NodeRelationItem(city1,city2,cityDistance));
        	neightboring.put(city2, data2);
        }
        
        //System.out.println(neightboring.toString());
        
        int S = input.nextInt();
        int D = input.nextInt();
        
        //System.out.println("We want to get from " + S + " to " + D);
        
        int numberOfBlocks = input.nextInt();
        
        //System.out.println("There will be " + numberOfBlocks + " blocks.");
        
        
        for (int i=0 ; i< numberOfBlocks; i++)
        {
        	if(S == D)
        	{
        		System.out.println(0);
        		continue;
        	}
        	long distance = 0;
        	
            int blockedRoad1 = input.nextInt();
            int blockedRoad2 = input.nextInt();
            if(blockedRoad1 > blockedRoad2)
            {
            	int tmp = blockedRoad1;
            	blockedRoad1 = blockedRoad2;
            	blockedRoad2 = tmp;
            }
            
            //System.out.println("There is blocked road from: " + blockedRoad1 + " to: " + blockedRoad2);
            
            int[] markedCities = new int[cities];
            long[] distanceWeights = new long[cities];
            HashMap<Integer, ArrayList<NodeRelationItem>> neighboringTmp = (HashMap<Integer, ArrayList<NodeRelationItem>>) neightboring.clone();
            
            ArrayList<NodeRelationItem> nri = neighboringTmp.getOrDefault(S, new ArrayList<NodeRelationItem>());
            markedCities[S] = 1;
            distanceWeights[S] = 0;
            for(NodeRelationItem item : nri)
            {
            	if(item.getfirstNodeId() == blockedRoad1 && item.getsecondNodeId() == blockedRoad2) continue;
            	if(item.getfirstNodeId() < 0 || item.getfirstNodeId() >= cities || item.getsecondNodeId() < 0 || item.getsecondNodeId() >= cities) continue;
            	queue.add(item);
            }
            
            NodeRelationItem nextNode;
            while((nextNode = queue.poll()) != null && nextNode.getfirstNodeId()!= D && nextNode.getsecondNodeId() != D)
            {	
            	int destinatinNode = -1;
            	int sourceNode = 0;
            	if(markedCities[nextNode.getfirstNodeId()] == 0)
            	{
            		destinatinNode = nextNode.getfirstNodeId();
            		sourceNode = nextNode.getsecondNodeId();
            	}
            	else if(markedCities[nextNode.getsecondNodeId()] == 0)
            	{
            		destinatinNode = nextNode.getsecondNodeId();
            		sourceNode = nextNode.getfirstNodeId();
            	}
            	
            	if(destinatinNode == (-1)) continue;
            	
            	distance += nextNode.getDistance();
            	markedCities[destinatinNode] = 1;
            	distanceWeights[destinatinNode] = distanceWeights[sourceNode] + nextNode.getDistance();
            	
            	//System.out.println("Updating: " + destinatinNode + " from: " + sourceNode + " to distance: " + distanceWeights[destinatinNode]);
            	
            	nri = neighboringTmp.get(destinatinNode);
                for(NodeRelationItem item : nri)
                {
                	if(item.getfirstNodeId() == blockedRoad1 && item.getsecondNodeId() == blockedRoad2) continue;
                	if(item.getfirstNodeId() < 0 || item.getfirstNodeId() >= cities || item.getsecondNodeId() < 0 || item.getsecondNodeId() >= cities) continue;
                	queue.add(item);
                }
            }
            
            if(nextNode == null) {
            	System.out.println("Infinity");
            	continue;
            }
            
            if(nextNode.getfirstNodeId() == D)
            {
            	System.out.println((long)(distanceWeights[nextNode.getsecondNodeId()] + nextNode.getDistance()));
            }
            else
            {
            	System.out.println((long)(distanceWeights[nextNode.getfirstNodeId()] + nextNode.getDistance()));
            }
            
            queue.clear();
        }
        
        
        //System.out.println("Finished data parsing");
    	
    }
}