package matrix.morpheus.chain.node;

import javassist.CtMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by poets11 on 15. 12. 3..
 */
public class UriNode implements Node {
    private String id;
    private String uri;
    private String[] methodNames;

    public UriNode(CtMethod ctMethod) {
        try {
            RequestMapping requestMapping = (RequestMapping) ctMethod.getAnnotation(RequestMapping.class);
            String[] uris = requestMapping.value();
            if (uris != null && uris.length > 0) {
                uri = uris[0];
            } else {
                uri = ctMethod.getName();
            }

            if (uri.startsWith("/") == false) {
                uri = "/" + uri;
            }

            id = uri;

            RequestMethod[] methods = requestMapping.method();
            StringBuilder methodNamesString = new StringBuilder();
            if (methods != null && methods.length > 0) {
                methodNames = new String[methods.length];

                for (int i = 0; i < methods.length; i++) {
                    RequestMethod method = methods[i];

                    if (i > 0) {
                        methodNamesString.append(", ");
                    }

                    methodNames[i] = method.name();
                    methodNamesString.append(method.name());
                }

                id += " - (" + methodNamesString.toString() + ")";
            } else {
                id += " - (ALL)";
            }
        } catch (ClassNotFoundException e) {
            uri = "/?";
            id = "/?";
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UriNode { " + id + " }";
    }
}


