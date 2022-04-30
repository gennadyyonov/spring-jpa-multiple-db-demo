package lv.gennadyyonov.spring.jpa.multiple.db.dao.user;

import lv.gennadyyonov.spring.jpa.multiple.db.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
