package org.itstep.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
//JPQL - обязательно узнать!
    //JpaRepository  отличия CrudRepository
    @Query("SELECT user FROM User user WHERE user.username = :username")
    public User getUserByUsername(@Param("username") String username);
}