package leetcode14.topologicalsort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * 210. There are a total of n courses you have to take, labeled from 0 to n - 1.

Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]

Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.

There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.

For example:

2, [[1,0]]
There are a total of 2 courses to take. To take course 1 you should have finished course 0. So the correct course order is [0,1]

4, [[1,0],[2,0],[3,1],[3,2]]
There are a total of 4 courses to take. To take course 3 you should have finished both courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. So one correct course order is [0,1,2,3]. Another correct ordering is[0,2,1,3].

Note:
The input prerequisites is a graph represented by a list of edges, not adjacency matrices. Read more about how a graph is represented.

click to show more hints.

Hints:
This problem is equivalent to finding the topological order in a directed graph. If a cycle exists, no topological ordering exists and therefore it will be impossible to take all courses.
Topological Sort via DFS - A great video tutorial (21 minutes) on Coursera explaining the basic concepts of Topological Sort.
Topological sort could also be done via BFS.
 */
public class CourseSchedule2 {
    public int[] findOrderGood(int N, int[][] P) {
        Map<Integer, Set<Integer>> courses = new HashMap<Integer, Set<Integer>>();
        Map<Integer, Integer> degrees = new HashMap<Integer, Integer>();
        int[] res = new int[N];
        int index = 0;
        
        for (int i = 0; i < P.length; i++) {
            int first = P[i][0];
            int second = P[i][1]; // first relies on second, second is the prerequisite
            
            if (!courses.containsKey(second)) {
                courses.put(second, new HashSet<Integer>());
            }
            courses.get(second).add(first);
            degrees.put(first, degrees.getOrDefault(first, 0) + 1);
        }
        
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < N; i++) {
            if (!degrees.containsKey(i)) {
                queue.offer(i);
            }
        }
        
        while (!queue.isEmpty()) {
            int now = queue.poll();
            res[index++] = now;
            
            if (courses.containsKey(now)) {
                for (int next : courses.get(now)) {
                    degrees.put(next, degrees.get(next) - 1);
                    if (degrees.get(next) == 0) {
                        queue.offer(next);
                    }
                }
            }
        }
        
        if (index == N) {
            return res;
        }
        return new int[]{}; // No solution!
    }
    
    public int[] findOrder2(int numCourses, int[][] prerequisites) {
        int[] res = new int[numCourses];
        ArrayList[] graph = new ArrayList[numCourses];
        int[] degree = new int[numCourses];
        Queue<Integer> queue = new LinkedList<Integer>();
        int count = numCourses; // Need to use reversed order
        
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        
        for (int i = 0; i < prerequisites.length; i++) {
            degree[prerequisites[i][1]]++;
            graph[prerequisites[i][0]].add(prerequisites[i][1]);
        }
        
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 0) {
                queue.offer(i);
                count--;
                res[count] = i;
            }
        }
        
        // BFS
        while (!queue.isEmpty()) {
            int course = queue.poll();
            for (int i = 0; i < graph[course].size(); i++) {
                int pre = (int)graph[course].get(i);
                degree[pre]--;
                if (degree[pre] == 0) {
                    queue.offer(pre);
                    count--;
                    res[count] = pre;
                }
            }
        }
        
        if (count == 0) {
            return res;
        }
        return new int[]{};
    }
}
