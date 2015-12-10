package matrix.morpheus.chain;

import matrix.morpheus.AnalyzerConfig;
import matrix.morpheus.chain.node.ChainLinker;

/**
 * Created by poets11 on 15. 12. 2..
 */
public class DefaultChainLinkerFactory implements ChainLinkerFactory {
    @Override
    public ChainLinker newChainLinker(AnalyzerConfig analyzerConfig) {
        return new DefaultChainLinker(analyzerConfig);
    }
}
