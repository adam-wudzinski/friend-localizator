package com.project.user;

        import com.project.user.User;
        import org.springframework.data.neo4j.repository.GraphRepository;
        import org.springframework.stereotype.Repository;

        import java.util.List;

/**
 * Created by Adas on 2017-04-18.
 */
@Repository
public interface UserRepository extends GraphRepository<User> {

    User findByEmail(String email);
    List<User> findByLastnameIgnoreCaseContaining(String lastname);



}
