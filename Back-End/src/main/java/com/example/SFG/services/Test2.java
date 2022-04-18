public class Test2 {
    private ForwardPathsGetter pathsGetter;

    @Autowired
    public Test2(ForwardPathsGetter pathsGetter) {
        this.pathsGetter = pathsGetter;
    }

    public void test(){
        Map<Node, String> toNeighbours1 = new HashMap<>();
        Map<Node, String> toNeighbours2 = new HashMap<>();
        Map<Node, String> toNeighbours3 = new HashMap<>();
        Map<Node, String> toNeighbours4 = new HashMap<>();
        Map<Node, String> toNeighbours5 = new HashMap<>();
//        Map<Node, String> toNeighbours5 = new HashMap<>();

        Node node1 = new Node("A");
        Node node2 = new Node("B");
        Node node3 = new Node("C");
        Node node4 = new Node("D");
        Node node5 = new Node("E");

        toNeighbours1.put(node2, "5");

        toNeighbours2.put(node3, "6");

        toNeighbours3.put(node4, "3");
        toNeighbours3.put(node5, "7");
        toNeighbours3.put(node1, "2");

        toNeighbours4.put(node5, "4");

        toNeighbours5.put(node4, "1");

        node1.setToNeighbours(toNeighbours1);
        node2.setToNeighbours(toNeighbours2);
        node3.setToNeighbours(toNeighbours3);
        node4.setToNeighbours(toNeighbours4);
        node5.setToNeighbours(toNeighbours5);

        List<Node> vertices = new ArrayList<>();
        vertices.add(node1);
        vertices.add(node2);
        vertices.add(node3);
        vertices.add(node4);
        vertices.add(node5);

        pathsGetter.setVertices(vertices);
        pathsGetter.setSourceIndex(0);
        pathsGetter.setSinkIndex(vertices.size() - 1);
        pathsGetter.getPaths();
    }
}