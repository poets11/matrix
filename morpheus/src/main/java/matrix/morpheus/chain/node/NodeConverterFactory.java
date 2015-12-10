package matrix.morpheus.chain.node;

import javassist.CtMethod;
import matrix.morpheus.chain.Chain;

/**
 * Created by poets11 on 15. 12. 2..
 */
public class NodeConverterFactory {
    private NodeConvertListener nodeConvertListener = new DefaultNodeConverter();
    private NodeConverter nodeConverter = new DefaultNodeConverter();

    public Chain createAndSetCurrentNode(Chain currentChain, CtMethod ctMethod) {
        currentChain = nodeConvertListener.beforeConvert(currentChain, ctMethod);
        currentChain = nodeConverter.convertAndSetCurrentNode(currentChain, ctMethod);
        currentChain = nodeConvertListener.afterConvert(currentChain, ctMethod);

        return currentChain;
    }

    public void setNodeConverter(NodeConverter nodeConverter) {
        this.nodeConverter = nodeConverter;
    }

    public void setNodeConvertListener(NodeConvertListener nodeConvertListener) {
        this.nodeConvertListener = nodeConvertListener;
    }
}
