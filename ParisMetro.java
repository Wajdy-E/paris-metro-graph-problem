import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParisMetro {

	private static Graph subwayGraph;

    public static void main(String[] args) {
        subwayGraph = readMetro("metro.txt");
        if (subwayGraph != null) {
            //subwayGraph.printAdjList();
            if (args.length == 1) {
                int station = Integer.parseInt(args[0]);
                List<Integer> dfsResult = subwayGraph.sameLine(station);
                System.out.println("DFS Result: " + dfsResult);
            }else if (args.length == 2) {
                int startStation = Integer.parseInt(args[0]);
                int endStation = Integer.parseInt(args[1]);
                subwayGraph.shortestPath(startStation, endStation);
            }
             else if (args.length == 3) {
                int startStation = Integer.parseInt(args[0]);
                int endStation = Integer.parseInt(args[1]);
                int brokenLine = Integer.parseInt(args[2]);
                subwayGraph.brokenLine(startStation, endStation, brokenLine);
            } else {
                System.out.println("Please enter valid arguments.");            
            }
        }
        
    }
    
    public static Graph readMetro(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            line = reader.readLine(); // we skip the first line as its not needed.
            String[] parts;
            Vertex newVertex;
    
            subwayGraph = new Graph();
    
            while ((line = reader.readLine()) != null) {
                if (line.equals("$")) {
                    // reached end
                    break;
                }
    
                parts = line.split("\\s+");
                try {
                    int stationNumber = Integer.parseInt(parts[0].trim());
                    String stationName = parts[1].trim();
                    newVertex = new Vertex(stationNumber, stationName, findEdges(stationNumber));
                    //System.out.print("this is size " + newVertex.getEdge());
                    //for(int i = 0; i<5;i++){
                    //    System.out.println("Station ID: " + newVertex.getStationID() + " Station name: " + newVertex.getStationName() + " Edge " + newVertex.getSize() );
                    //} prints 0 for edge list size???
                    
                    subwayGraph.addVertex(newVertex);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("FAIL");
                }
            }
    
            reader.close();
            return subwayGraph;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //finds the list of edges for a given stationID
    private static ArrayList<Edge> findEdges(int stationNum) {
        List<List<String>> lines = readElementsAfter();
        ArrayList<Edge> matchingEdges = new ArrayList<>();
    
        for (List<String> line : lines) {
            int currentStationNum;
            try {
                currentStationNum = Integer.parseInt(line.get(0));
            } catch (NumberFormatException e) {
                continue;
            }
    
            if (currentStationNum == stationNum) {
                try {
                    int destination = Integer.parseInt(line.get(1));
                    int weight = Integer.parseInt(line.get(2));    
                    // Create a new Edge object and add it to the list
                    matchingEdges.add(new Edge(destination, weight));
                } catch (IndexOutOfBoundsException | NumberFormatException  e) {
                    continue;
                }
            }
        }
        
        //for(Edge edges: matchingEdges){
           // System.out.print(edges.getConnectedStationID() + " " + edges.getWeight() + " to");
        //}
        //System.out.println(matchingEdges.size());
        return matchingEdges;
    }    
    
    
    //add each line after the $ to a list.
    public static List<List<String>> readElementsAfter() {
            List<List<String>> lines = new ArrayList<>();
    
            try (BufferedReader reader = new BufferedReader(new FileReader("metro.txt"))) {
                String line;
                boolean after = false;
    
                while ((line = reader.readLine()) != null) {
                    if (after) {
                        if (line.equals("$")) {
                            // Reached the end, lines after $ processed
                            after = false;
                        } else {
                            String[] parts = line.split("\\s+");
    
                            // Add parts to the list
                            List<String> lineParts = new ArrayList<>();
                            for (String part : parts) {
                                lineParts.add(part);
                            }
                            lines.add(lineParts);
                        }
                    }
    
                    if (line.equals("$")) {
                        // Found $, start capturing lines
                        after = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            return lines;
        }

        
        

}