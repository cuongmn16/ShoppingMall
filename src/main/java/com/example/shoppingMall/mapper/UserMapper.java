package com.example.shoppingMall.mapper;

import com.example.shoppingMall.dto.request.UserCreationRequest;
import com.example.shoppingMall.dto.request.UserUpdateRequest;
import com.example.shoppingMall.dto.response.UserResponse;
import com.example.shoppingMall.enums.AccountStatus;
import com.example.shoppingMall.enums.Gender;
import com.example.shoppingMall.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    void toUpdateUser(@MappingTarget User user, UserUpdateRequest request);

    @Mapping(source = "gender", target = "gender",qualifiedByName = "stringToGender")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth", qualifiedByName = "stringToLocalDate")
    @Mapping( target = "accountStatus", expression = "java(com.example.shoppingMall.enums.AccountStatus.ACTIVE)")
    User toUser(UserCreationRequest userCreationRequest);

    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderToString")
    @Mapping(source = "dateOfBirth", target = "dateOfBirth", qualifiedByName = "localDateToString")
    @Mapping(source = "accountStatus", target = "accountStatus", qualifiedByName = "accountStatusToString")
    UserResponse toUserResponse(User user);

    @Named("stringToGender")
    default Gender stringToGender(String gender) {
        if(gender == null || gender.isEmpty()){
            return null;
        }
        try{
            return Gender.valueOf(gender);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid gender :" + gender);
        }
    }

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateOfBirth, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format: " + dateOfBirth, e);
        }
    }

    @Named("localDateToString")
    default String localDateToString(LocalDate dateOfBirth) {
        return dateOfBirth != null ? dateOfBirth.format(DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }

    @Named("accountStatusToString")
    default String accountStatusToString(AccountStatus status) {
        return status != null ? status.name() : null;
    }
    @Named("genderToString")
    default String genderToString(Gender gender) {
        return gender != null ? gender.name() : null;
    }

}

