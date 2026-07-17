package com.nextcart.service;

import com.nextcart.model.UserAddress;

import java.util.List;
import java.util.Optional;

public interface UserAddressService {
    Optional<UserAddress> getAddressById(Integer id);
    List<UserAddress> getAddressesByUserId(Integer userId);
    Optional<UserAddress> getDefaultAddressByUserId(Integer userId);
    UserAddress createAddress(UserAddress address);
    UserAddress updateAddress(UserAddress address);
    boolean deleteAddress(Integer id);
}
