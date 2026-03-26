//package pms_core.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import pms_core.model.request.LoginRequest;
//import pms_core.model.response.TokenResponse;
//import pms_core.security.JwtService;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class AuthService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService service;
//
//    public TokenResponse authenticate(LoginRequest request){
//        String encode = passwordEncoder.encode(request.getPassword());
//        log.info("encode password : {}", encode);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
//        Authentication authenticated = authenticationManager.authenticate(authentication);
//        return service.generateToken(authenticated);
//    }
//}
