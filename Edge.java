public class Edge {
    private int weight;
    private int connectedStationID;

    public Edge(int connectedStationID, int weight){
        if(weight == -1){
            this.weight = 90;
        }else{
            this.weight = weight;
        }
        this.connectedStationID = connectedStationID;
    }

    public int getWeight() {
        return weight;
    }

    public int getConnectedStationID() {
        return connectedStationID;
    }

    @Override
    public String toString() {
        return "(" + connectedStationID + ", " + weight + ")";
    }
}
