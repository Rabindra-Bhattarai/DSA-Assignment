import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Qn5 extends JPanel {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private final ArrayList<Edge> edges = new ArrayList<>();
    private final JButton optimizeButton, addEdgeButton;
    private Node selectedNode1, selectedNode2;

    public Qn5() {
        setPreferredSize(new Dimension(600, 400));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                nodes.add(new Node(e.getX(), e.getY(), nodes.size()));
                repaint();
            }
        });

        optimizeButton = new JButton("Optimize Network");
        optimizeButton.addActionListener(e -> optimizeNetwork());

        addEdgeButton = new JButton("Add Edge");
        addEdgeButton.addActionListener(e -> addEdge());
    }

    private void addEdge() {
        if (nodes.size() < 2)
            return;
        String input = JOptionPane.showInputDialog("Enter edge (node1 index, node2 index, cost, bandwidth):");
        if (input != null) {
            String[] parts = input.split(",");
            if (parts.length == 4) {
                try {
                    int i1 = Integer.parseInt(parts[0].trim());
                    int i2 = Integer.parseInt(parts[1].trim());
                    int cost = Integer.parseInt(parts[2].trim());
                    int bandwidth = Integer.parseInt(parts[3].trim());
                    if (i1 >= 0 && i1 < nodes.size() && i2 >= 0 && i2 < nodes.size() && i1 != i2) {
                        edges.add(new Edge(nodes.get(i1), nodes.get(i2), cost, bandwidth));
                        repaint();
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }

    private void optimizeNetwork() {
        edges.clear();
        if (nodes.size() < 2)
            return;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = i + 1; j < nodes.size(); j++) {
                int cost = (int) Math.sqrt(
                        Math.pow(nodes.get(i).x - nodes.get(j).x, 2) + Math.pow(nodes.get(i).y - nodes.get(j).y, 2));
                pq.add(new Edge(nodes.get(i), nodes.get(j), cost, 10));
            }
        }

        DisjointSet ds = new DisjointSet(nodes.size());
        while (!pq.isEmpty() && edges.size() < nodes.size() - 1) {
            Edge edge = pq.poll();
            int u = nodes.indexOf(edge.n1);
            int v = nodes.indexOf(edge.n2);
            if (ds.find(u) != ds.find(v)) {
                ds.union(u, v);
                edges.add(edge);
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Edge e : edges) {
            g.setColor(Color.BLACK);
            g.drawLine(e.n1.x, e.n1.y, e.n2.x, e.n2.y);
            g.drawString("Cost: " + e.cost + ", BW: " + e.bandwidth, (e.n1.x + e.n2.x) / 2, (e.n1.y + e.n2.y) / 2);
        }
        for (Node n : nodes) {
            g.setColor(Color.BLUE);
            g.fillOval(n.x - 5, n.y - 5, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString("N" + n.id, n.x + 5, n.y - 5);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Network Optimizer");
        Qn5 panel = new Qn5();
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        JButton btnOptimize = new JButton("Optimize Network");
        JButton btnAddEdge = new JButton("Add Edge");
        btnOptimize.addActionListener(e -> panel.optimizeNetwork());
        btnAddEdge.addActionListener(e -> panel.addEdge());
        buttonPanel.add(btnOptimize);
        buttonPanel.add(btnAddEdge);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class Node {
    int x, y, id;

    Node(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }
}

class Edge {
    Node n1, n2;
    int cost, bandwidth;

    Edge(Node n1, Node n2, int cost, int bandwidth) {
        this.n1 = n1;
        this.n2 = n2;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }
}

class DisjointSet {
    private final int[] parent, rank;

    DisjointSet(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++)
            parent[i] = i;
    }

    int find(int i) {
        if (parent[i] != i)
            parent[i] = find(parent[i]);
        return parent[i];
    }

    void union(int i, int j) {
        int ri = find(i), rj = find(j);
        if (ri != rj) {
            if (rank[ri] > rank[rj])
                parent[rj] = ri;
            else if (rank[ri] < rank[rj])
                parent[ri] = rj;
            else {
                parent[rj] = ri;
                rank[ri]++;
            }
        }
    }
}
