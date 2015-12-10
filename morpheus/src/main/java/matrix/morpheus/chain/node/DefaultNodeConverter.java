package matrix.morpheus.chain.node;

import javassist.CtMethod;
import matrix.morpheus.chain.Chain;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by poets11 on 15. 12. 3..
 */
public class DefaultNodeConverter implements NodeConverter, NodeConvertListener {
    private Map<String, Node> nodeMap = new HashMap<String, Node>();

    @Override
    public Chain convertAndSetCurrentNode(Chain currentChain, CtMethod ctMethod) {
        MethodNode currentNode = new MethodNode(ctMethod);

        Node node = nodeMap.get(currentNode.getId());
        if (node != null) {
            currentChain.setNode(node);
        } else {
            nodeMap.put(currentNode.getId(), currentNode);
            currentChain.setNode(currentNode);
        }

        return currentChain;
    }

    @Override
    public Chain beforeConvert(Chain currentChain, CtMethod ctMethod) {
        return currentChain;
    }

    @Override
    public Chain afterConvert(Chain currentChain, CtMethod ctMethod) {
        try {
            if (currentChain.getSeq() > 0) {
                return currentChain;
            }

            Object annotation = ctMethod.getAnnotation(RequestMapping.class);
            if (annotation == null) {
                return currentChain;
            }

            int seq = currentChain.getSeq();
            int depth = currentChain.getDepth();

            Chain chain = new Chain();
            chain.setSeq(seq);
            chain.setDepth(depth);
            chain.appendSubChain(currentChain);

            currentChain.setSeq(seq + 1);
            currentChain.setDepth(depth + 1);
            currentChain.setParentChain(chain);

            UriNode uriNode = new UriNode(ctMethod);
            Node node = nodeMap.get(uriNode.getId());
            if (node != null) {
                chain.setNode(node);
            } else {
                chain.setNode(uriNode);
                nodeMap.put(uriNode.getId(), uriNode);
            }

            return currentChain;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return currentChain;
    }
}
