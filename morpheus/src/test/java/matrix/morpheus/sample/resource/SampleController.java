package matrix.morpheus.sample.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by poets11 on 15. 12. 2..
 */
@Controller
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @RequestMapping("/sample/hello.do")
    public String hello() {
        sampleService.abstractMethodInInterface();
        return null;
    }
}
