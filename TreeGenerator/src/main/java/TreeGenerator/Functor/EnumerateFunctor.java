package TreeGenerator.Functor;

import TreeGenerator.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class EnumerateFunctor implements Functor {
    private Random random;
    private List<Integer> numbers;

    public EnumerateFunctor(int amount, Random random) {
        numbers = new LinkedList<>();
        this.random = random;
        for (int i = 0; i < amount; i++) {
            numbers.add(i);
        }
    }

    @Override
    public void apply(TreeNode node) {
        int index = random.nextInt(numbers.size());
        int value = numbers.get(index);
        numbers.remove(index);
        node.setValue(value);
    }
}
