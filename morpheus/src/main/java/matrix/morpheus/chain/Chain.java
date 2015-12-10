package matrix.morpheus.chain;

import matrix.morpheus.chain.node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poets11 on 15. 12. 2..
 */
public class Chain {
    private int seq;
    private int depth;
    private Node node;
    private Chain parentChain;
    private List<Chain> subChains = new ArrayList<Chain>();

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Chain getParentChain() {
        return parentChain;
    }

    public void setParentChain(Chain parentChain) {
        this.parentChain = parentChain;
    }

    public List<Chain> getSubChains() {
        return subChains;
    }

    public void appendSubChain(Chain subChain) {
        subChains.add(subChain);
    }

    @Override
    public String toString() {
        return "Chain{" +
                "seq=" + seq +
                ", depth=" + depth +
                ", node=" + node +
                ", subChains=" + subChains +
                '}';
    }
}
