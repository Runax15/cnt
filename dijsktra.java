//implement dijkstra using adjacency matrix, witohout priority queue, take in source and target

import java.util.*;

public class dijsktra {
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of vertices");
        int n = sc.nextInt();
        int[][] graph = new int[n][n];
        System.out.println("Enter the adjacency matrix");
        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                graph[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter the source vertex");
        int source = sc.nextInt();
        System.out.println("Enter the target vertex");
        int target = sc.nextInt();
        int[] distance = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) 
        {
            distance[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        distance[source] = 0;
        for (int i = 0; i < n - 1; i++) 
        {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < n; j++) 
            {
                if (!visited[j] && distance[j] < min) 
                {
                    min = distance[j];
                    minIndex = j;
                }
            }
            visited[minIndex] = true;
            for (int j = 0; j < n; j++) 
            {
                if (!visited[j] && graph[minIndex][j] != 0 && distance[minIndex] != Integer.MAX_VALUE 
                    && distance[minIndex] + graph[minIndex][j] < distance[j]) 
                {
                    distance[j] = distance[minIndex] + graph[minIndex][j];
                }
            }
        }
        System.out.println("The shortest distance from " + source + " to " + target + " is " + distance[target]);
        sc.close();
    }
}
/* 
5

0 2 3 3 5
2 0 4 1 3
3 4 0 2 4
3 1 2 0 2
5 3 4 2 0 
*/