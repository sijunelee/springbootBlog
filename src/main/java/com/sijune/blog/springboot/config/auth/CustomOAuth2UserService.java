package com.sijune.blog.springboot.config.auth;

import com.sijune.blog.springboot.config.auth.dto.OAuthAttributes;
import com.sijune.blog.springboot.config.auth.dto.SessionUser;
import com.sijune.blog.springboot.domain.user.User;
import com.sijune.blog.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

//구글 로그인 이후 가져온 사용자 정보 기반으로 어떤 기능할 지
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //로그인 진행중인 서비스 구분 : 구글, 네이버, 카카오...
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); //로그인 진행 시 키가 되는 필드값(pk)

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); //유저의 속성을 담는다.

        User user = saveOrUpdate(attributes); //로그인 이후 변경 사항 업데이트
        httpSession.setAttribute("user", new SessionUser(user)); //세션 업데이트

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail()).map(entity->entity.update(attributes.getName(), attributes.getPicture())).orElse(attributes.toEntity());
        //앞부분이 update, 뒷부분이 save
        return userRepository.save(user);
    }
}
