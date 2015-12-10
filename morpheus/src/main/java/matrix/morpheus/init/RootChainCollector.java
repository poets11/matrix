package matrix.morpheus.init;

import matrix.morpheus.AnalyzerConfig;
import matrix.morpheus.chain.Chain;

import java.util.List;

/**
 * Created by poets11 on 15. 7. 15..
 */
public interface RootChainCollector {
    List<Chain> getRootChains(AnalyzerConfig assembleConfig);
}
