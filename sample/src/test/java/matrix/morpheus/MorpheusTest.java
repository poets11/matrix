package matrix.morpheus;

import matrix.morpheus.chain.node.DefaultNodeConverter;
import matrix.morpheus.chain.node.NodeConverterFactory;
import matrix.morpheus.expression.access.rule.ClassFinderFactory;
import matrix.morpheus.expression.access.rule.DefaultClassFinder;
import matrix.morpheus.expression.access.rule.NamingRuleClassFinder;
import matrix.morpheus.init.RequestMappingRootChainCollector;
import org.junit.Test;

/**
 * Created by poets11 on 15. 12. 1..
 */
public class MorpheusTest {
    @Test
    public void morpheus() {
        AnalyzerConfig config = new AnalyzerConfig();
        config.setBasePackage("matrix.sample.member");
        config.setRootChainCollector(new RequestMappingRootChainCollector());

        config.setClassFinderFactory(getClassFinderFactory());
        config.setNodeConverterFactory(getConverterFactory());

        MorpheusAnalyzer analyzer = new MorpheusAnalyzer();
        analyzer.setAnalyzerConfig(config);

        analyzer.analyze();
    }

    private NodeConverterFactory getConverterFactory() {
        DefaultNodeConverter nodeConverter = new DefaultNodeConverter();

        NodeConverterFactory factory = new NodeConverterFactory();
        factory.setNodeConverter(nodeConverter);
        factory.setNodeConvertListener(nodeConverter);

        return factory;
    }

    private ClassFinderFactory getClassFinderFactory() {
        ClassFinderFactory classFinderFactory = new ClassFinderFactory();
        classFinderFactory.addClassFinder(new DefaultClassFinder());

        NamingRuleClassFinder namingRuleClassFinder = new NamingRuleClassFinder();
        namingRuleClassFinder.setSuffix("Impl");
        classFinderFactory.addClassFinder(namingRuleClassFinder);

        return classFinderFactory;
    }
}
