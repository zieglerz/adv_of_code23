import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;

public class App {

    private static final int REMOVE_CONNECTIONS_COUNT = 3;

    public static void main(String[] args) throws Exception {
        String diagramFilePath = "res" + System.getProperty("file.separator") + "big_diagram.txt";

        File f = new File(diagramFilePath);
        if (!f.exists()) {
            System.out.println("Invalid path: " + f.getCanonicalPath());
            return;
        }

        Map<String, List<String>> diagram = loadDiagram(diagramFilePath);
        
        // printDiagram(diagram);

        List<String[]> connectionsToRemove = findConnectionToRemove(diagram);
        for (String[] connection : connectionsToRemove) {
            diagram.get(connection[0]).remove(connection[1]);
            diagram.get(connection[1]).remove(connection[0]);
        }

        List<Integer> sizes = calculateSubgraphSizes(diagram);
        int result = 1;
        for (int size : sizes) {
            result *= size;
        }
        System.out.println("Result: " + result);
    }

    private static Map<String, List<String>> loadDiagram(String filePath) throws IOException {
        Map<String, List<String>> diagram = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String component = parts[0];
                String[] neighbors = parts[1].trim().split(" ");
                diagram.putIfAbsent(component, new ArrayList<>());
                for (String neighbor : neighbors) {
                    diagram.get(component).add(neighbor);

                    diagram.putIfAbsent(neighbor, new ArrayList<>());
                    diagram.get(neighbor).add(component);
                }
            }
        }
        return diagram;
    }

    private static List<String[]> findConnectionToRemove(Map<String, List<String>> diagram) {
        Map<String, Integer> connectionBetweenness = calculateConnectionBetweenness(diagram);
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry<String, Integer>::getValue).reversed());
        pq.addAll(connectionBetweenness.entrySet());

        List<String[]> connectionToRemove = new ArrayList<>();
        for (int i = 0; i < REMOVE_CONNECTIONS_COUNT && !pq.isEmpty(); i++) {
            String edge = pq.poll().getKey();
            String[] nodes = edge.split("-");
            connectionToRemove.add(nodes);
        }
        return connectionToRemove;
    }

    private static Map<String, Integer> calculateConnectionBetweenness(Map<String, List<String>> diagram) {
        Map<String, Integer> connectionBetweenness = new HashMap<>();
        for (String node : diagram.keySet()) {
            bfsForBetweenness(diagram, node, connectionBetweenness);
        }
        return connectionBetweenness;
    }

    private static void bfsForBetweenness(Map<String, List<String>> diagram, String start, Map<String, Integer> connectionBetweenness) {
        Map<String, Integer> distance = new HashMap<>();
        Map<String, Integer> numShortestPaths = new HashMap<>();
        Map<String, List<String>> predecessors = new HashMap<>();
        Stack<String> visited = new Stack<>();
        Queue<String> toVisit = new LinkedList<>();

        // initialize data structures for each component
        for (String component : diagram.keySet()) {
            distance.put(component, -1);
            numShortestPaths.put(component, 0);
            predecessors.put(component, new ArrayList<>());
        }
        
        distance.put(start, 0);
        numShortestPaths.put(start, 1);
        toVisit.add(start);

        while (!toVisit.isEmpty()) {
            String component = toVisit.poll();
            visited.push(component);
            for (String neighbor : diagram.get(component)) {
                if (distance.get(neighbor) == -1) {
                    distance.put(neighbor, distance.get(component) + 1);
                    toVisit.add(neighbor);
                }
                if (distance.get(neighbor) == distance.get(component) + 1) {
                    numShortestPaths.put(neighbor, numShortestPaths.get(neighbor) + numShortestPaths.get(component));
                    predecessors.get(neighbor).add(component);
                }
            }
        }

        Map<String, Double> dependency = new HashMap<>();
        for (String node : diagram.keySet()) {
            dependency.put(node, 0.0);
        }

        while (!visited.isEmpty()) {
            String node = visited.pop();
            for (String predecessor : predecessors.get(node)) {
                double ratio = (double) numShortestPaths.get(predecessor) / numShortestPaths.get(node);
                double value = ratio * (1.0 + dependency.get(node));
                String edge = predecessor.compareTo(node) < 0 ? predecessor + "-" + node : node + "-" + predecessor;
                connectionBetweenness.put(edge, connectionBetweenness.getOrDefault(edge, 0) + (int) value);
                dependency.put(predecessor, dependency.get(predecessor) + value);
            }
        }
    }

    private static List<Integer> calculateSubgraphSizes(Map<String, List<String>> graph) {
        List<Integer> sizes = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                int size = bfs(graph, node, visited);
                sizes.add(size);
            }
        }
        return sizes;
    }

    private static int bfs(Map<String, List<String>> graph, String start, Set<String> visited) {
        Queue<String> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        int size = 0;
        while (!queue.isEmpty()) {
            String node = queue.poll();
            size++;
            for (String neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return size;
    }

    private static void printDiagram(Map<String, List<String>> diagram) {
        for (Map.Entry<String, List<String>> entry : diagram.entrySet()) {
            System.out.println(entry.getKey() + ": " + String.join(" ", entry.getValue()));
        }
    }
}
