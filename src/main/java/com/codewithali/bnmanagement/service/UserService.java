package com.codewithali.bnmanagement.service;

import com.codewithali.bnmanagement.dto.UserDto;
import com.codewithali.bnmanagement.dto.response.SuccessReponse;
import com.codewithali.bnmanagement.model.Role;
import com.codewithali.bnmanagement.model.User;
import com.codewithali.bnmanagement.repository.UserRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    public UserService(UserRepository userRepository, MongoTemplate mongoTemplate) {
        this.userRepository = userRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream().map(UserDto::convertToUserDto)
                .collect(Collectors.toList());
    }

    public SuccessReponse changeUserRole(String id, Set<Role> role) throws RuntimeException {
        try {
            Query query = new Query(Criteria.where("id").is(id));
            Update update = new Update().set("roles", role);
            mongoTemplate.updateFirst(query, update, User.class);
            return new SuccessReponse("Kullanıcı Güncellendi", HttpStatus.OK.value());
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public SuccessReponse deleteUser(String id) throws RuntimeException {
        userRepository.deleteById(id);
        return new SuccessReponse("Kullanıcı Kaldırıldı", HttpStatus.OK.value());
    }
}
