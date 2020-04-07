package TreeGenerator;

import java.util.LinkedList;
import java.util.List;

public class TreeNode {
    private int value;
    private List<TreeNode> children;

    TreeNode(int val) {
        value = val;
        children = new LinkedList<>();
    }

    public void addChild(TreeNode node) {
        children.add(node);
    }

    public int getAmountOfChildren() {
        return children.size();
    }

    public TreeNode getChild(int index) {
        return children.get(index);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
