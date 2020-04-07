package TreeGenerator.Functor;

import TreeGenerator.TreeNode;

public class CountFunctor implements Functor {
    private int counter = 0;

    public int getCounter() {
        return counter;
    }

    @Override
    public void apply(TreeNode node) {
        counter++;
    }
}
