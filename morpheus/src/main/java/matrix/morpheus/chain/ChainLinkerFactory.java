package matrix.morpheus.chain;

import matrix.morpheus.AnalyzerConfig;
import matrix.morpheus.chain.node.ChainLinker;

/**
 * Created by poets11 on 15. 12. 2..
 */
public interface ChainLinkerFactory {
    public ChainLinker newChainLinker(AnalyzerConfig analyzerConfig);
}
