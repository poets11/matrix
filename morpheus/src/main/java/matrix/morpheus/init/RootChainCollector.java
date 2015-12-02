package matrix.morpheus.init;

import javassist.CtMethod;
import matrix.morpheus.AnalyzerConfig;

import java.util.List;

/**
 * Created by poets11 on 15. 7. 15..
 */
public interface RootChainCollector {
    List<CtMethod> getRootChains(AnalyzerConfig assembleConfig);
}
