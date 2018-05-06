package com.github.chenjianhua.repository;

/**
 * @author Fang
 *
 */
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import javax.persistence.Table;
import com.github.chenjianhua.entity.User;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
@Table(name="user")
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select t from User t where t.name=:name")
    public User findUserByName(@Param("name") String name);
}
