package com.delivery.delivery_app.api.controller.account;


import com.delivery.delivery_app.api.controller.common.ApiResponse;
import com.delivery.delivery_app.api.controller.account.request.AccountJoinRequest;
import com.delivery.delivery_app.api.controller.account.request.AccountloginRequest;
import com.delivery.delivery_app.api.controller.account.response.AccountJoinResponse;
import com.delivery.delivery_app.api.controller.account.response.AccountLoginResponse;
import com.delivery.delivery_app.api.service.account.AccountService;
import com.delivery.delivery_app.domain.account.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    // TODO implement
    @PostMapping("/join")
    public ApiResponse<AccountJoinResponse> join(@RequestBody AccountJoinRequest request) {

        AccountEntity joinAccountEntity = accountService.join(request.getNickname(), request.getPassword());

        return ApiResponse.success(AccountJoinResponse.of(joinAccountEntity));
    }

    @PostMapping("/login")
    public ApiResponse<AccountLoginResponse> login(@RequestBody AccountloginRequest request) {

        String token = accountService.login(request.getNickname(), request.getPassword());

        return ApiResponse.success(new AccountLoginResponse(token));
    }

}
