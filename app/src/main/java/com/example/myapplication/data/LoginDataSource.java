package com.example.myapplication.data;

import com.example.myapplication.data.model.LoggedInUser;

import java.io.IOException;


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {



    public Result<LoggedInUser> login(String username, String password) {


//        boolean isLogin = true;
//        if (true) {
//
//
//            try {
//                // TODO: handle loggedInUser authentication
//                LoggedInUser fakeUser =
//                        new LoggedInUser(
//                                java.util.UUID.randomUUID().toString(),
//                                "Jane Doe");
//                return new Result.Success<>(fakeUser);
//            } catch (Exception e) {
//                return new Result.Error(new IOException("Error logging in", e));
//            }
//
//
//        }
//        else {
//            return new Result.Error(new IOException("用户名或密码错误！"));
//        }
        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
