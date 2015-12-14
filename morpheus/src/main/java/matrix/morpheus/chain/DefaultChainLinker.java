package matrix.morpheus.chain;

import javassist.CannotCompileException;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.MethodCall;
import matrix.morpheus.AnalyzerConfig;
import matrix.morpheus.chain.node.ChainLinker;
import matrix.morpheus.chain.node.NodeConverterFactory;
import matrix.morpheus.expression.access.rule.ClassFinderFactory;

/**
 * Created by poets11 on 15. 12. 2..
 */
public class DefaultChainLinker extends ChainLinker {
    private AnalyzerConfig analyzerConfig;
    private Chain currentChain;
    private int currentSeq;

    public DefaultChainLinker(AnalyzerConfig analyzerConfig) {
        this.analyzerConfig = analyzerConfig;
    }

    @Override
    public void edit(MethodCall m) throws CannotCompileException {
        try {
            ClassFinderFactory classFinderFactory = analyzerConfig.getClassFinderFactory();
            NodeConverterFactory nodeConverterFactory = analyzerConfig.getNodeConverterFactory();

            if (m.getClassName().startsWith(analyzerConfig.getBasePackage())) {
                CtMethod ctMethod = classFinderFactory.findActualClass(currentChain.getNode(), lastAccessedField, m.getMethod());
                if (ctMethod != null) {
                    Chain chain = new Chain();
                    chain.setDepth(currentChain.getDepth() + 1);
                    chain.setSeq(++currentSeq);
                    chain.setParentChain(currentChain);

                    currentChain.appendSubChain(chain);
                    currentChain = nodeConverterFactory.createAndSetCurrentNode(chain, ctMethod);
                    if (isRecursive(currentChain) == false) {
                        ctMethod.instrument(this);
                    }
                    currentChain = currentChain.getParentChain();
                } else {
                    System.out.println("__ can not find actual method : " + m.getClassName() + "." + m.getMethodName() + "();");
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    protected boolean isRecursive(Chain currentChain) {
        String id = currentChain.getNode().getId();

        Chain parent = currentChain.getParentChain();
        while (parent != null) {
            if (id.equals(parent.getNode().getId())) {
                return true;
            }

            parent = parent.getParentChain();
        }

        return false;
    }

    @Override
    public Chain link(Chain rootChain) throws CannotCompileException {
        currentChain = rootChain;
        currentSeq = rootChain.getSeq();

        CtMethod ctMethod = getCurrentNodeMethod(rootChain);
        if (ctMethod != null) {
            ctMethod.instrument(this);
        }

        return getParentChain(currentChain);
    }

    private Chain getParentChain(Chain rootChain) {
        Chain parentChain = rootChain.getParentChain();
        if (parentChain == null) {
            return rootChain;
        } else {
            return getParentChain(parentChain);
        }
    }
}
