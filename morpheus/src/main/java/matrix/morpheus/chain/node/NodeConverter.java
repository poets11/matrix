package matrix.morpheus.chain.node;

import javassist.CtMethod;
import matrix.morpheus.chain.Chain;

/**
 * Created by poets11 on 15. 12. 3..
 */
public interface NodeConverter {
    Chain convertAndSetCurrentNode(Chain currentChain, CtMethod ctMethod);
}
