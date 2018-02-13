import com.alibaba.fastjson.JSONObject;
import com.jwh.demo.CommonRequest;
import com.jwh.demo.StartServer;
import com.jwh.demo.controller.UserController;
import com.jwh.demo.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class ConcurrentTest {

    private UserController userController;

    @Before
    public void startUp(){
        StartServer.main(new String[]{});
        userController = StartServer.context.getBean("userController",UserController.class);
    }

    @Test
    public void test() throws Exception{
        JSONObject json = new JSONObject();
        json.put("id",12);
        CommonRequest request = new CommonRequest(json);
        List<User> users =  userController.get(request);
        users.forEach(user -> System.out.println(user.getUserName()));
    }

}
