package hello.core.logdemo;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

//    private final ObjectProvider<MyLogger> myLoggerProvider; // Provider 사용

    private final MyLogger myLogger; // 프록시 사용

    public void logic(String id) {
//        MyLogger myLogger = myLoggerProvider.getObject(); // Provider 사용
        myLogger.log("service id = " + id);
    }

}