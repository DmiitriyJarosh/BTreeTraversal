package TreeGenerator.Functor;

import TreeGenerator.TreeNode;

public class MtxPrintFunctor implements Functor {

    private CountFunctor counter = new CountFunctor();
    private int edgeCounter = 0;
    private StringBuilder output = new StringBuilder("");

    @Override
    public void apply(TreeNode node) {
        counter.apply(node);
        for (int i = 0; i < node.getAmountOfChildren(); i++) {
            edgeCounter++;
            output.append(node.getValue())
                    .append(" ")
                    .append(node.getChild(i).getValue())
                    .append(" 1\n");
        }
    }

    public String getOutput() {
        String matrixInfo = counter.getCounter() + " " + counter.getCounter() + " " + edgeCounter + "\n";
        String header = "%%MatrixMarket matrix coordinate integer general\n" +
                "%%GraphBLAS GrB_BOOL\n";
        return header + matrixInfo + output.toString();
    }
}
