//package pms_core.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pms_core.model.request.LoginRequest;
//import pms_core.model.response.TokenResponse;
//import pms_core.service.AuthService;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final AuthService service;
//
//    @PostMapping
//    public TokenResponse authenticate(LoginRequest request){
//       return service.authenticate(request);
//    }
//}
