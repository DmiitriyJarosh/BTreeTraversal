package TreeGenerator;

import TreeGenerator.Functor.CountFunctor;
import TreeGenerator.Functor.EnumerateFunctor;
import TreeGenerator.Functor.Functor;
import TreeGenerator.Functor.MtxPrintFunctor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TreeGenerator {

    private Random random = new Random();

    public void generateStructure(TreeNode node, int depth) {
        if (depth >= 10) {
            return;
        }
        int amount = random.nextInt(8) - 2;
        for (int i = 0; i < amount; i++) {
            TreeNode newNode = new TreeNode(-1);
            node.addChild(newNode);
            generateStructure(newNode, depth + 1);
        }
    }

    public void dfsWithFunctor(Tree tree, Functor functor) {
       TreeNode node = tree.getRoot();
       dfsWithFunctor(node, functor);
    }

    public void dfsWithFunctor(TreeNode node, Functor functor) {
        if (node == null) {
            return;
        }
        functor.apply(node);
        for (int i = 0; i < node.getAmountOfChildren(); i++) {
            dfsWithFunctor(node.getChild(i), functor);
        }
    }

    public Tree generateTree() {
        Tree tree = new Tree(new TreeNode(-1));

        generateStructure(tree.getRoot(), 0);

        CountFunctor counter = new CountFunctor();
        dfsWithFunctor(tree, counter);
        int amount = counter.getCounter();

        dfsWithFunctor(tree, new EnumerateFunctor(amount, random));

        return tree;
    }

    public void printTree(Tree tree, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            MtxPrintFunctor printer = new MtxPrintFunctor();
            dfsWithFunctor(tree, printer);
            writer.write(printer.getOutput());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void generateDataset(int amountOfTrees) {
        for (int i = 0; i < amountOfTrees; i++) {
            System.out.println("Generating " + i + "th tree!");
            Tree tree = generateTree();
            if (tree.getRoot().getAmountOfChildren() == 0) {
                i--;
                continue;
            }
            printTree(tree, "test" + (i + 1) + ".mtx");
        }
    }

}
