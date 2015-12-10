package matrix.morpheus.chain.node;

import javassist.CtMethod;
import matrix.morpheus.chain.Chain;

/**
 * Created by poets11 on 15. 12. 3..
 */
public interface NodeConvertListener {
    Chain beforeConvert(Chain currentChain, CtMethod ctMethod);

    Chain afterConvert(Chain currentChain, CtMethod ctMethod);
}
