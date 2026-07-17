package com.nextcart.dao;

import com.nextcart.model.UserAddress;
import java.util.List;
import java.util.Optional;

public interface UserAddressDAO {
    Optional<UserAddress> findById(Integer addressId);
    List<UserAddress> findByUserId(Integer userId);
    Optional<UserAddress> findDefaultByUserId(Integer userId);
    UserAddress create(UserAddress address);
    UserAddress update(UserAddress address);
    boolean delete(Integer addressId);
}
