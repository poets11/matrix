package matrix.morpheus.sample.resource;

import org.springframework.stereotype.Service;

/**
 * Created by poets11 on 15. 12. 2..
 */
@Service
public class SampleServiceImpl extends AbstractSampleService implements SampleService {
    @Override
    public void abstractMethodInAbstract() {
        concreteMethodInAbstract();
        concreateMethodInImplements();
    }

    @Override
    public void abstractMethodInInterface() {
        abstractMethodInAbstract();
    }

    public void concreateMethodInImplements() {

    }
}
