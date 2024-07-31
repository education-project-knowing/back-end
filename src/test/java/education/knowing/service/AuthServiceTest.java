//package education.knowing.service;
//
//import education.knowing.constant.Role;
//import education.knowing.dto.ResponseDto;
//import education.knowing.dto.auth.request.CertificationRequestDto;
//import education.knowing.dto.auth.request.SignUpRequestDto;
//import education.knowing.dto.auth.response.CertificationResponseDto;
//import education.knowing.dto.auth.response.SignUpResponseDto;
//import education.knowing.entity.User;
//import education.knowing.exception.BusinessError;
//import education.knowing.exception.BusinessLogicException;
//import education.knowing.repository.EmailCertificationRepository;
//import education.knowing.repository.UserRepository;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//class AuthServiceTest {
//    @Mock
//    UserRepository userRepository;
//
//    @Mock
//    PasswordEncoder passwordEncoder;
//
//    @Mock
//    EmailCertificationRepository emailCertificationRepository;
//
//    @InjectMocks
//    AuthService authService;
//
//    @Test
//    void 회원가입() {
//        //given
//        SignUpRequestDto signUpRequestDto = new SignUpRequestDto();
//        User user = User.builder()
//                .id(1L)
//                .username(signUpRequestDto.getUsername())
//                .password(signUpRequestDto.getPassword())
//                .email(signUpRequestDto.getEmail())
//                .nickname(signUpRequestDto.getNickname())
//                .role(Role.USER)
//                .build();
//        when(passwordEncoder.encode(Mockito.any())).thenReturn("1234");
//        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
//
//        //when
//        SignUpResponseDto responseDto = authService.join(signUpRequestDto);
//
//        assertEquals(1L, responseDto.getUserId());
//    }
//
//    @Test
//    void 인증번호_생성() {
//        //given
//        CertificationRequestDto certificationDto = new CertificationRequestDto("abc123@abc.com", null);
//
//        //when
////        CertificationResponseDto result = authService.sendCertificationEmail(certificationDto);
//
//        //then
////        assertNotNull(result.getCertificationNumber());
//    }
//
//    @Test
//    void 인증_성공() {
//        //given
//        CertificationRequestDto certificationDto = new CertificationRequestDto("abc123@abc.com", "123456");
//        when(emailCertificationRepository.existsByEmailAndCertificationNumber(certificationDto.getEmail(), certificationDto.getCertificationNumber())).thenReturn(true);
//
//        //when
//        ResponseDto<?> responseDto = authService.certificationEmail(certificationDto);
//
//        //then
//        assertEquals(responseDto.getStatus_code(),200);
//        assertEquals(responseDto.getMessage(),"이메일 인증 성공");
//    }
//
//    @Test
//    void 인증_실패() {
//        //given
//        CertificationRequestDto certificationDto = new CertificationRequestDto("abc123@abc.com", "123456");
//        when(emailCertificationRepository.existsByEmailAndCertificationNumber(certificationDto.getEmail(), certificationDto.getCertificationNumber())).thenReturn(false);
//
//        //when
//        BusinessLogicException e = assertThrows(BusinessLogicException.class,()->{
//            authService.certificationEmail(certificationDto);
//        });
//
//        //then
//        BusinessError businessError = e.getBusinessError();
//        assertNotNull(businessError);
//        assertEquals(businessError.getStatus(),400);
//        assertEquals(businessError.getMessage(),"이메일 인증 실패");
//    }
//}