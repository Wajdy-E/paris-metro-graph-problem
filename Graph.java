import java.util.*;
import java.util.stream.Collectors;

public class Graph {
    private ArrayList<Vertex> adjList;

    public Graph() {
        this.adjList = new ArrayList<>();
    }

    public void addVertex(Vertex vertex) {
        adjList.add(vertex);
    }
    /* checking adjList is correct
    public void printAdjList() {
        for (Vertex vertex : adjList) {
            System.out.print(vertex.getStationID() + ": [");

            ArrayList<Edge> edges = vertex.getEdge();
            for (Edge edgesInlist : edges) {
                System.out.print(edgesInlist + " , ");
            }
            System.out.println("]");
        }
    }
    */
    //solution for 2.i
    public List<Integer> sameLine(int stationID) {
        Set<Integer> stationsOnSameLine = new LinkedHashSet<>(); // We want to keep ordering so a LinkedHashSet is used
        Set<Integer> visitedStations = new HashSet<>(); //keep track of what stations have been visited to check if set already contains an edge

        dfs(stationID, stationsOnSameLine, visitedStations);

        return new ArrayList<>(stationsOnSameLine);
    }
    //implementing depth first search for 2.i
    private void dfs(int currStation, Set<Integer> stationsOnSameLine, Set<Integer> visitedStations) {
        stationsOnSameLine.add(currStation);
        visitedStations.add(currStation);

        for (Edge edge : getEdges(currStation)) {
            int nextStation = edge.getConnectedStationID();
            int weight = edge.getWeight();
            //do not add paths with weight of 90
            if (weight != 90 && !visitedStations.contains(nextStation)) {
                dfs(nextStation, stationsOnSameLine, visitedStations);
            }
        }
    }

    private List<Edge> getEdges(int stationID) {
        for (Vertex vertex : adjList) {
            if (vertex.getStationID() == stationID) {
                return vertex.getEdge();
            }
        }
        return Collections.emptyList();
    }

    //implementation for 2.ii using Dijkstra's algorithm 
    public List<Integer> shortestPath(int startStation, int endStation) {
        Map<Integer, Integer> distances = new HashMap<>(); //keep track of distances
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Map<Integer, Integer> previous = new HashMap<>();    
        for (Vertex vertex : adjList) {
            int stationID = vertex.getStationID();
            distances.put(stationID, Integer.MAX_VALUE);
            previous.put(stationID, -1); 
        }
    
        distances.put(startStation, 0);
        queue.add(startStation);
    
        while (!queue.isEmpty()) {
            Integer curr = queue.poll();
            if (curr != null) {
                for (Edge neighbor : getEdges(curr)) {
                    int alt = distances.get(curr) + neighbor.getWeight();
    
                    if (alt < distances.get(neighbor.getConnectedStationID())) {
                        distances.put(neighbor.getConnectedStationID(), alt);
                        previous.put(neighbor.getConnectedStationID(), curr);
                        queue.add(neighbor.getConnectedStationID());
                    }
                }
            }
        }
        List<Integer> graphPath = new ArrayList<>();
        int curr = endStation;
    
        while (curr != -1) {
            graphPath.add(curr);
            curr = previous.get(curr);
        }

        Collections.reverse(graphPath);
    
        System.out.println("Shortest Path from Station " + startStation + " to Station " + endStation + ": " + graphPath);
        System.out.println("Total travel time: " + distances.get(endStation) + " seconds");
    
        return graphPath;
    }
    
    //solution for 2.iii using Dijkstra's algorithm 
    public List<Integer> brokenLine(int startStation, int endStation, int brokenLineEndpoint) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        Set<Integer> brokenLine = sameLine(brokenLineEndpoint).stream().collect(Collectors.toSet());
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Vertex vertex : adjList) {
            distances.put(vertex.getStationID(), Integer.MAX_VALUE);
            previous.put(vertex.getStationID(), -1);
        }
        

        distances.put(startStation, 0);
        queue.add(startStation);

        while (!queue.isEmpty()) {
            Integer curr = queue.poll();

            if (curr != null) {
                for (Edge neighbor : getEdges(curr)) {
                    int alt = distances.get(curr) + neighbor.getWeight();

                    // Check if the neighbor belongs to the broken line
                    if (brokenLine.contains(neighbor.getConnectedStationID())) {
                        continue;
                    }

                    if (alt < distances.get(neighbor.getConnectedStationID())) {
                        distances.put(neighbor.getConnectedStationID(), alt);
                        previous.put(neighbor.getConnectedStationID(), curr);
                        queue.add(neighbor.getConnectedStationID());
                    }
                }
            }
        }

            List<Integer> graphPath = new ArrayList<>();
            int curr = endStation;
        
            while (curr != -1) {
                graphPath.add(curr);
                curr = previous.getOrDefault(curr, -1);
            }
            
        
            Collections.reverse(graphPath);

        // Print the result
        System.out.println("Shortest Path from Station " + startStation + " to Station " + endStation + ": " + graphPath);
        System.out.println("Total travel time: " + distances.get(endStation) + " seconds");

        return graphPath;
}

    
    
    
}

