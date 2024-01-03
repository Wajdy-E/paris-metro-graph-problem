import java.util.ArrayList;
import java.util.Collections;

public class Vertex {
    private int stationID;
    private String stationName;
    private ArrayList<Edge> edge;
    
    public Vertex(int stationID, String stationName, ArrayList<Edge> edge) {
        this.stationID = stationID;
        this.stationName = stationName;
        //Collections.reverse(edge);
        this.edge = edge;   
    }

    public int getStationID() {
        return this.stationID;
    }

    public ArrayList<Edge> getEdge() {
        return this.edge;
    }

    public String getStationName(){
        return this.stationName;
    }

    public int getSize(){
        return this.edge.size();
    }

        @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(stationID).append(": [");

        for (int i = 0; i < edge.size(); i++) {
            Edge edgeInArray = edge.get(i);
            sb.append(edgeInArray);

            if (i < edge.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }



}
