package com.abao.as.repository.common;

import com.abao.as.model.common.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface UserRepository extends CrudRepository<User, Long>, JpaSpecificationExecutor<User> {

    User findByUsername(String username);
}
