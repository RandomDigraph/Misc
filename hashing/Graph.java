/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashing;

/**
 *
 * @author Quack <Quack.a@school.horse>
 */
public class Graph {
    private int MAX_NUMBER_OF_NODES;
    private Dictionary<Character,Dictionary<Character,Integer>> adjacencyList;
    
    public static void main(String[] args) {
        Graph graph = new Graph(10);
        graph.addUndirectedEdge('A','B',5);
        graph.addUndirectedEdge('A','D',8);
        graph.addUndirectedEdge('A','E',4);
        graph.addUndirectedEdge('B','C',4);
        graph.addUndirectedEdge('C','D',5);
        graph.addUndirectedEdge('C','G',2);
        graph.addUndirectedEdge('D','E',7);
        graph.addUndirectedEdge('D','F',6);
        System.out.println(graph.toString());
    }
    
    public Graph(int size){
        MAX_NUMBER_OF_NODES = size;
        adjacencyList = new Dictionary<>(MAX_NUMBER_OF_NODES);
    }
    
    public void addDirectedEdge(Character sourceNode, Character destinationNode, int weight){
        if(!adjacencyList.contains(sourceNode)) addNode(sourceNode);
        if(!adjacencyList.contains(destinationNode)) addNode(destinationNode);
        adjacencyList.item(sourceNode).add(destinationNode, weight);
    }
    public void addUndirectedEdge(Character node1, Character node2, int weight){
        if(!adjacencyList.contains(node1)) addNode(node1);
        if(!adjacencyList.contains(node2)) addNode(node2);
        adjacencyList.item(node1).add(node2, weight);
        adjacencyList.item(node2).add(node1, weight);
    }
    private void addNode(Character node_name){
        adjacencyList.add(node_name, new Dictionary<Character,Integer>(MAX_NUMBER_OF_NODES));
    }
    /*private void removeNode(Character nodeToDelete){
        Character[] allNodes = (Character[]) adjacencyList.getKeys();
        for(Character node: allNodes){
            if (!adjacencyList.item(node).contains(nodeToDelete)) adjacencyList.item(node).delete(nodeToDelete);
        }
        adjacencyList.delete(nodeToDelete);
    }
    public Character[] listOfNeighbours(Character node){
        return adjacencyList.item(node).getKeys();
    }*/
    
    public int size(){
        return adjacencyList.length();
    }
    public int max_size(){
        return adjacencyList.maxSize();
    }
    @Override
    public String toString(){
        /*  Format looks like this 
            GRAPH = {
                'A':{'B':5,'D':8,'E':4},\n
                'B':{'A':5,'C’:4},
                'C':{'B':4,'D':5,'G':2},
                'D':{'A':8,'C':5,'E':7,'F':6},
                'E':{'A':4,'D':7},
                'F':{'D':6},
                'G':{'C':2}
            }
        */
        String output = adjacencyList.toString();
        output = "GRAPH = {\n\t"+output.substring(1,output.length());
        output = output.replaceAll("},", "},\n\t");
        output = output.replaceFirst("}}", "}\n}");
        return output;
    }
}