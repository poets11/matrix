package matrix.morpheus.write;

import javassist.CtMethod;

/**
 * Created by poets11 on 15. 7. 15..
 */
public interface TraceWriter {
    String writeInitTraceInfo(CtMethod ctMethod) throws ClassNotFoundException;
    boolean writeTraceInfo(TraceInfo traceInfo);
}
