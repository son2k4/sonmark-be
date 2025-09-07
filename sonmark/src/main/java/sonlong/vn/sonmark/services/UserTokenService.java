package sonlong.vn.sonmark.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sonlong.vn.sonmark.entity.UserToken;
import sonlong.vn.sonmark.repository.UserTokenRepository;

import java.time.LocalDateTime;

@Service
public class UserTokenService {
    @Autowired
    private UserTokenRepository userTokenRepository ;

    public void saveLatestToken(String username, String token){
        UserToken userToken = new UserToken() ;
        userToken.setUsername(username);
        userToken.setToken(token);
        userToken.setCreateAt(LocalDateTime.now());
        userTokenRepository.save(userToken) ;
    }

    public String getCurrentToken(String username){
        return userTokenRepository.findById(username).map(UserToken::getToken).orElse(null) ;
    }
}
