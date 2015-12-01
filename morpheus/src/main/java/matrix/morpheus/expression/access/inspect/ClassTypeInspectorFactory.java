package matrix.morpheus.expression.access.inspect;

import javassist.CtField;
import javassist.CtMethod;
import matrix.morpheus.expression.access.ClassType;
import matrix.morpheus.expression.access.ClassTypeInspector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by poets11 on 15. 7. 20..
 */
public class ClassTypeInspectorFactory {
    private List<ClassTypeInspector> classTypeInspectors = new ArrayList<ClassTypeInspector>();
    
    public ClassTypeInspectorFactory() {
        
    }

    public ClassTypeInspectorFactory(Class<? extends ClassTypeInspector>... classTypeInspectorClasses) throws IllegalAccessException, InstantiationException {
        for (int i = 0; i < classTypeInspectorClasses.length; i++) {
            Class<? extends ClassTypeInspector> classTypeInspectorClass = classTypeInspectorClasses[i];
            classTypeInspectors.add(classTypeInspectorClass.newInstance());
        }
    }

    public ClassTypeInspectorFactory(String[] classTypeInspectorNames) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (int i = 0; i < classTypeInspectorNames.length; i++) {
            String classTypeInspectorName = classTypeInspectorNames[i];
            ClassTypeInspector classTypeInspector = (ClassTypeInspector) Class.forName(classTypeInspectorName).newInstance();
            classTypeInspectors.add(classTypeInspector);
        }
    }

    public ClassType inspect(CtField accessedField, CtMethod ctMethod) {

        for (int i = 0; i < classTypeInspectors.size(); i++) {
            try {
                ClassTypeInspector classTypeInspector = classTypeInspectors.get(i);
                ClassType classType = classTypeInspector.inspect(accessedField, ctMethod);

                if (classType != null && ClassType.UNKNOWN.equals(classType) == false) {
                    return classType;
                }
            } catch (ClassNotFoundException e) {
            }
        }


        return ClassType.UNKNOWN;
    }

    public List<ClassTypeInspector> getClassTypeInspectors() {
        return classTypeInspectors;
    }

    public void setClassTypeInspectors(List<ClassTypeInspector> classTypeInspectors) {
        this.classTypeInspectors = classTypeInspectors;
    }

    public void addClassTypeInspector(ClassTypeInspector classTypeInspector) {
        classTypeInspectors.add(classTypeInspector);
    }
}
