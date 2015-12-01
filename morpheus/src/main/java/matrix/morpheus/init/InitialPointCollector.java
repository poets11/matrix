package matrix.morpheus.init;

import javassist.CtMethod;
import matrix.morpheus.AssembleConfig;

import java.util.List;

/**
 * Created by poets11 on 15. 7. 15..
 */
public interface InitialPointCollector {
    List<CtMethod> getInitialPoints(AssembleConfig assembleConfig);
}
