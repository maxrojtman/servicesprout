package com.Maxwell.ServiceSprout.service.interfac;

import com.Maxwell.ServiceSprout.dto.LoginRequest;
import com.Maxwell.ServiceSprout.dto.Response;
import com.Maxwell.ServiceSprout.entity.User;

public interface IUserService {
    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserWorkOrders(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);

}
