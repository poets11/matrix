package matrix.morpheus.write;

import javassist.CtMethod;
import matrix.morpheus.expression.access.ClassType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static matrix.morpheus.expression.access.ClassType.URL;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class FileTraceWriter implements TraceWriter {
    private static String TRACE_FORMAT = "%s|%s|%s|%s|%s|%s|%s";

    private List<ClassType> traceTargetClassTypes;
    private Map<Integer, String> depthClassNameMap;

    public FileTraceWriter() {
        traceTargetClassTypes = new ArrayList<ClassType>();
        traceTargetClassTypes.add(URL);
        traceTargetClassTypes.add(ClassType.CONTROLLER);
        traceTargetClassTypes.add(ClassType.SERVICE);
        traceTargetClassTypes.add(ClassType.REPOSITORY);
        traceTargetClassTypes.add(ClassType.UTIL);
        
        depthClassNameMap = new HashMap<Integer, String>();
    }

    @Override
    public String writeInitTraceInfo(CtMethod ctMethod) throws ClassNotFoundException {
        Object annotation = ctMethod.getAnnotation(RequestMapping.class);
        if (annotation == null) {
            return null;
        }

        TraceInfo root = new TraceInfo(1, 0, URL, ctMethod);
        root.setMethodName(getUrlFromRequestMapping(annotation));
        boolean rootResult = writeTraceInfo(root);

        TraceInfo controller = new TraceInfo(2, 1, ClassType.CONTROLLER, ctMethod);
        boolean controllerResult = writeTraceInfo(controller);

        if (rootResult && controllerResult) {
            return root.getKey();
        } else {
            return null;
        }
    }

    private String getUrlFromRequestMapping(Object annotation) {
        RequestMapping requestMapping = (RequestMapping) annotation;
        String[] value = requestMapping.value();
        if (value != null && value.length > 0) {
            return value[0];
        } else {
            return "";
        } 
    }

    @Override
    public boolean writeTraceInfo(TraceInfo traceInfo) {
        boolean traceTarget = isSatisfyTraceTarget(traceInfo.getClassType());
        if (traceTarget) {
            System.out.println(getTraceMessage(traceInfo));
            return true;
        }

        return false;
    }

    private String getTraceMessage(TraceInfo info) {
//        return String.format(TRACE_FORMAT, info.getKey(), info.getSeq(), info.getDepth(),
//                info.getClassType().getValue(), info.getPackageName(), info.getClassName(), info.getMethodName());
//    	if(info.getDepth() == 0) {
//    		depthClassNameMap.put(0, info.getMethodName());
//    		return "";
//    	} else {
//    		depthClassNameMap.put(info.getDepth(), info.getClassName());
//    		String parentClassName = depthClassNameMap.get(info.getDepth() - 1);
//    		return parentClassName + "->" + info.getClassName() + " : " + info.getMethodName();
//    	}
    	
//    	return info.getClassName() + "-> : " + info.getMethodName();

        // DEBUG
        
        if (URL.equals(info.getClassType())) {
            return info.getMethodName();
        }

        StringBuilder sb = new StringBuilder();
        if (info.getDepth() > 1) {
            for (int i = 1; i < info.getDepth(); i++) {
                sb.append(" ");
            }
            sb.append("└ ");
        }

        sb.append(info.getClassName() + "." + info.getMethodName() + "();");
        return sb.toString();
    }

    private boolean isSatisfyTraceTarget(ClassType classType) {
        for (int i = 0; i < traceTargetClassTypes.size(); i++) {
            ClassType type = traceTargetClassTypes.get(i);
            if (type.equals(classType)) {
                return true;
            }
        }

        return false;
    }
}
