package cn.com.bmac.wolf.customer.mapper;


import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Hashtable;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Data
    class User{
        private int userId;
        private String moblieNo;
        private String password;
        public  User(int userId,String moblieNo,String password){
            this.userId = userId;
            this.moblieNo = moblieNo;
            this.password = password;
        }

    }

    Map<String,User> userMap = new Hashtable<>();

    @GetMapping(value = "/user/{userId}/{password}")
    public String login(@PathVariable("userId") int userId,@PathVariable("password") String password){
        String loginkey = userId + password;
        if (!userMap.containsKey(loginkey)){
            return "1";
        }
        return "0";
    }


    @PostMapping(value = "/user")
    public String register(@RequestBody User user){
        int userId = pseudoEncrypt(user.getMoblieNo());
        User registerUser = new User();
        registerUser.setMoblieNo(user.getMoblieNo());
        registerUser.setUserId(userId);
        registerUser.setPassword(user.getPassword());
        String loginkey = registerUser.getUserId() + registerUser.getPassword();
        if (userMap.containsKey(loginkey)){
            return "1";
        }
        userMap.put(loginkey,registerUser);
        return "0";
    }

    public  int pseudoEncrypt(String mobileNo){
        int h = 0;
        char[] value = mobileNo.toCharArray();
        for (char val : value) {
            h += val;
        }
        int l1 = (h >> 16) & 0xffff;
        int r1 = h & 0xffff;
        int l2, r2;
        int i;
        for (i = 0; i < 3; i++) {
            l2 = r1;
            r2 = l1 ^ (int)(Math.round(((((1366*r1 + 150889) % 714025) / 714025.0) * 32767))) ;
            l1 = l2;
            r1 = r2;
        }
        return ((r1 << 16) +l1);
    }
}
