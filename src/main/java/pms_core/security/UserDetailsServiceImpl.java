//package pms_core.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import pms_core.model.response.UserResponse;
//import pms_core.service.UsersService;
//
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    private final UsersService usersService;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserResponse response = usersService.findByUsername(username);
//
//        return User.builder()
//                .username(response.getUsername())
//                .password(response.getPassword())
//                .authorities("ROLE_" + response.getRole())
//                .build();
//    }
//}
