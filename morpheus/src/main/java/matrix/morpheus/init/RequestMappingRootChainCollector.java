package matrix.morpheus.init;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import matrix.morpheus.AnalyzerConfig;
import matrix.morpheus.AssembleException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public List<CtMethod> getRootChains(AnalyzerConfig analyzerConfig) {
        try {
            List<CtMethod> rootChains = new ArrayList<CtMethod>();

            ClassPool pool = ClassPool.getDefault();
            Set<BeanDefinition> components = getFilteredComponents(analyzerConfig);
            for (Iterator<BeanDefinition> iterator = components.iterator(); iterator.hasNext(); ) {
                BeanDefinition beanDefinition = iterator.next();
                String className = beanDefinition.getBeanClassName();

                CtClass ctClass = pool.get(className);
                CtMethod[] ctMethods = ctClass.getDeclaredMethods();
                for (int i = 0; i < ctMethods.length; i++) {
                    CtMethod ctMethod = ctMethods[i];

                    Object annotation = ctMethod.getAnnotation(RequestMapping.class);
                    if (annotation != null) {
                        RequestMapping requestMapping = (RequestMapping) annotation;
                        String[] urls = requestMapping.value();

                        if (isMatchedWithPatterns(urls)) {
                            rootChains.add(ctMethod);
                        }
                    }
                }
            }

            return rootChains;
        } catch (Exception e) {
            throw new AssembleException(e);
        }
    }

    private boolean isMatchedWithPatterns(String[] urls) {
        if (urlPatterns.size() == 0) {
            return true;
        }

        AntPathMatcher matcher = new AntPathMatcher();

        for (int i = 0; i < urls.length; i++) {
            String url = urls[i];

            for (int j = 0; j < urlPatterns.size(); j++) {
                String pattern = urlPatterns.get(j);

                if (matcher.match(pattern, url)) {
                    return true;
                }
            }
        }

        return false;
    }

    private Set<BeanDefinition> getFilteredComponents(AnalyzerConfig analyzerConfig) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

        for (int i = 0; i < typeFilters.size(); i++) {
            TypeFilter typeFilter = typeFilters.get(i);
            scanner.addIncludeFilter(typeFilter);
        }

        return scanner.findCandidateComponents(analyzerConfig.getBasePackage());
    }
}
