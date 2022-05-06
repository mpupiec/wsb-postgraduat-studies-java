package pl.wsb.java.flightapp.model;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(Integer id);

    User save(User entity);


}
