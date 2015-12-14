package matrix.morpheus;

import matrix.morpheus.chain.node.DefaultNodeConverter;
import matrix.morpheus.chain.node.NodeConverterFactory;
import matrix.morpheus.expression.access.rule.*;
import matrix.morpheus.init.RequestMappingRootChainCollector;

public class MorpheusAnalyzerTest {
    public static void main(String[] args) {
        new MorpheusAnalyzerTest().morpheus();
    }

    public void morpheus() {
        AnalyzerConfig config = new AnalyzerConfig();
        config.setBasePackage("hanmi.ces");
        config.setRootChainCollector(new RequestMappingRootChainCollector());
        config.setWebRootPath("/Users/poets11/development/eclipse/workspace_beijing/beijing-ces/target/beijing-ces-1.0.0-SNAPSHOT");
        config.setTomcatHome("/Users/poets11/development/lib/apache-tomcat-7.0.57");

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

        MappedClassFinder mappedClassFinder = new MappedClassFinder();
        mappedClassFinder.put("hanmi.ces.beijing.work.kfReport.service.KfReportService", "hanmi.ces.beijing.work.kfReport.service.kfReportServiceImpl");
        mappedClassFinder.put("hanmi.ces.account.ces.ejunpyo.employee.service.EJunpyoEmployeeService.insertEmployee", "hanmi.ces.account.ces.ejunpyo.employee.service.AbstractEJunpyoEmployeeService");
        mappedClassFinder.put("hanmi.ces.account.ces.ejunpyo.employee.service.EJunpyoEmployeeService.insertEmployeeHistory", "hanmi.ces.account.ces.ejunpyo.employee.service.AbstractEJunpyoEmployeeService");
        mappedClassFinder.put("hanmi.ces.account.ces.ejunpyo.employee.service.EJunpyoEmployeeService.eJunpyoEmployeeSapSend", "hanmi.ces.account.ces.ejunpyo.employee.service.AbstractEJunpyoEmployeeService");
        mappedClassFinder.put("hanmi.ces.account.ces.ejunpyo.employee.service.EJunpyoEmployeeService.getNextEmployee", "hanmi.ces.account.ces.ejunpyo.employee.service.AbstractEJunpyoEmployeeService");
        classFinderFactory.addClassFinder(mappedClassFinder);

        FieldBaseClassFinder fieldBaseClassFinder = new FieldBaseClassFinder();
        fieldBaseClassFinder.put("EJunpyoApprovalAccountService", "hanmi.ces.account.ces.ejunpyo.approval.service.EJunpyoApprovalAccountServiceImpl");
        fieldBaseClassFinder.put("EJunpyoApprovalAccountRepository", "hanmi.ces.account.ces.ejunpyo.approval.service.repository.EJunpyoApprovalAccountRepositoryImpl");
        fieldBaseClassFinder.put("EJunpyoApprovalFileAccountService", "hanmi.ces.account.ces.ejunpyo.approval.service.EJunpyoApprovalFileAccountServiceImpl");
        fieldBaseClassFinder.put("EJunpyoEmployeeAccountService", "hanmi.ces.account.ces.ejunpyo.employee.service.EJunpyoEmployeeAccountServiceImpl");

        fieldBaseClassFinder.put("EJunpyoApprovalApService", "hanmi.ces.account.ces.ejunpyo.approval.service.EJunpyoApprovalApServiceImpl");
        fieldBaseClassFinder.put("EJunpyoApprovalApRepository", "hanmi.ces.account.ces.ejunpyo.approval.service.repository.EJunpyoApprovalApRepositoryImpl");
        fieldBaseClassFinder.put("EJunpyoApprovalFileApService", "hanmi.ces.account.ces.ejunpyo.approval.service.EJunpyoApprovalFileApServiceImpl");
        fieldBaseClassFinder.put("EJunpyoEmployeeApService", "hanmi.ces.account.ces.ejunpyo.employee.service.EJunpyoEmployeeApServiceImpl");

        classFinderFactory.addClassFinder(fieldBaseClassFinder);

        return classFinderFactory;
    }
}