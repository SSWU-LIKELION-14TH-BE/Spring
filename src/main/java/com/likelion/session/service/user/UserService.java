package com.likelion.session.service.user;
import com.likelion.session.entity.user.User;
import com.likelion.session.dto.user.request.UserLoginRequestDto;
import com.likelion.session.dto.user.request.UserSignupRequestDto;
import com.likelion.session.dto.user.response.UserLoginResponseDto;
import com.likelion.session.repository.user.UserRepository;
import com.likelion.session.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // 생성자 주입
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 🔑 회원가입 (비밀번호 암호화 후 저장)
    @Transactional
    public void signup(UserSignupRequestDto requestDto) {
        // 이미 가입된 이메일인지 확인
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .name(requestDto.getName())
                .profileImage(requestDto.getProfileImage())
                .build();

        userRepository.save(user);
    }

    // 🔐 로그인 (이메일, 비밀번호 검증 후 JWT 발급)
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        // 없는 유저일시 오류 출력
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 유저를 찾을 수 없습니다."));

        // 비밀번호 불일치시 오류 출력
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());

        return UserLoginResponseDto.builder()
                .email(user.getEmail())
                .token(token)
                .build();
    }

    // 🔍 Spring Security가 유저 정보를 가져올 때 사용하는 메서드
    // 로그인 후 다른 행위들을 할 때 jwt 토큰만 확인하는 것이 아닌, 유저가 db에 있는지 확인하는 과정
    // Spring Security를 사용하기 때문에, 그들이 이해하는 객체 형식으로 뱉어줘아 함
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다: " + email));

        // 우리가 만든 User 엔티티가 아니라, 시큐리티가 이해하는 User 객체로 변환
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("USER") // 기본 권한 설정
                .build();
    }
}
