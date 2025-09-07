package sonlong.vn.sonmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sonlong.vn.sonmark.entity.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken,String> {
}
