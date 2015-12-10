package matrix.morpheus.init;

import javassist.*;
import matrix.morpheus.AnalyzerConfig;
import matrix.morpheus.AssembleException;
import matrix.morpheus.chain.Chain;
import matrix.morpheus.chain.node.NodeConverterFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by poets11 on 15. 7. 15..
 */
public class RequestMappingRootChainCollector implements RootChainCollector {
    private List<String> urlPatterns;
    private List<TypeFilter> typeFilters;

    public RequestMappingRootChainCollector() {
        urlPatterns = new ArrayList<String>();

        typeFilters = new ArrayList<TypeFilter>();
        typeFilters.add(new AnnotationTypeFilter(Controller.class));
    }

    public void addTypeFilter(TypeFilter typeFilter) {
        typeFilters.add(typeFilter);
    }

    public void addUrlPattern(String antPathPattern) {
        urlPatterns.add(antPathPattern);
    }

    @Override
    public List<Chain> getRootChains(AnalyzerConfig analyzerConfig) {
        try {
            ClassPool pool = ClassPool.getDefault();
            NodeConverterFactory converterFactory = analyzerConfig.getNodeConverterFactory();

            List<Chain> rootChains = new ArrayList<Chain>();

            Set<BeanDefinition> components = getFilteredComponents(analyzerConfig);
            for (Iterator<BeanDefinition> iterator = components.iterator(); iterator.hasNext(); ) {
                BeanDefinition beanDefinition = iterator.next();
                String className = beanDefinition.getBeanClassName();

                if (className.startsWith(analyzerConfig.getBasePackage()) == false) {
                    continue;
                }

                CtClass ctClass = pool.get(className);
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();
                for (int i = 0; i < ctMethods.length; i++) {
                    CtMethod ctMethod = ctMethods[i];

                    Object annotation = ctMethod.getAnnotation(RequestMapping.class);
                    if (annotation != null) {
                        RequestMapping requestMapping = (RequestMapping) annotation;
                        String uri = ctMethod.getName();

                        String[] uris = requestMapping.value();
                        if (uris != null && uris.length > 0) {
                            uri = uris[0];
                        }

                        if (uri.startsWith("/") == false) {
                            uri = "/" + uri;
                        }

                        if (isMatchedWithPatterns(uri)) {
                            Chain chain = new Chain();
                            chain.setSeq(0);
                            chain.setDepth(0);

                            chain = converterFactory.createAndSetCurrentNode(chain, ctMethod);

                            rootChains.add(chain);
                        }
                    }
                }
            }

            return rootChains;
        } catch (Exception e) {
            throw new AssembleException(e);
        }
    }

    private String[] getRequestMethods(RequestMethod[] method) {
        String[] methodNames = null;

        if (method != null) {
            methodNames = new String[method.length];
            for (int i = 0; i < method.length; i++) {
                RequestMethod requestMethod = method[i];
                methodNames[i] = requestMethod.name();
            }
        }

        return methodNames;
    }

    private boolean isMatchedWithPatterns(String url) {
        if (urlPatterns.size() == 0) {
            return true;
        }

        AntPathMatcher matcher = new AntPathMatcher();

        for (int j = 0; j < urlPatterns.size(); j++) {
            String pattern = urlPatterns.get(j);

            if (matcher.match(pattern, url)) {
                return true;
            }
        }

        return false;
    }

    private Set<BeanDefinition> getFilteredComponents(AnalyzerConfig analyzerConfig) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.setResourceLoader(getResourceLoader(analyzerConfig));

        for (int i = 0; i < typeFilters.size(); i++) {
            TypeFilter typeFilter = typeFilters.get(i);
            scanner.addIncludeFilter(typeFilter);
        }

        return scanner.findCandidateComponents(analyzerConfig.getBasePackage());
    }

    private ResourceLoader getResourceLoader(AnalyzerConfig analyzerConfig) {
        try {
            List<URL> urlList = new ArrayList<URL>();

            urlList.add(new URL("file:///Users/poets11/development/eclipse/workspace_beijing/beijing-ces/target/classes/"));

            appendUrl(urlList, "/Users/poets11/development/eclipse/workspace_beijing/beijing-ces/target/beijing-ces-1.0.0-SNAPSHOT/WEB-INF/lib");
            appendUrl(urlList, "/Users/poets11/development/lib/apache-tomcat-7.0.57/lib");

            ClassLoader classLoader = new URLClassLoader(urlList.toArray(new URL[]{}));

            ClassPool pool = ClassPool.getDefault();
            ClassPath classPath = new LoaderClassPath(classLoader);
            pool.appendClassPath(classPath);

            return new DefaultResourceLoader(classLoader);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void appendUrl(List<URL> urlList, String dirPath) throws MalformedURLException {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.getName().endsWith("jar")) {
                urlList.add(new URL("file://" + file.getAbsolutePath()));
            }
        }
    }
}
