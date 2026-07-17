package com.nextcart.serviceImpl;

import com.nextcart.dao.UserAddressDAO;
import com.nextcart.daoImpl.UserAddressDAOImpl;
import com.nextcart.model.UserAddress;
import com.nextcart.service.UserAddressService;

import java.util.List;
import java.util.Optional;

public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressDAO userAddressDAO = new UserAddressDAOImpl();

    @Override
    public Optional<UserAddress> getAddressById(Integer id) {
        return userAddressDAO.findById(id);
    }

    @Override
    public List<UserAddress> getAddressesByUserId(Integer userId) {
        return userAddressDAO.findByUserId(userId);
    }

    @Override
    public Optional<UserAddress> getDefaultAddressByUserId(Integer userId) {
        return userAddressDAO.findDefaultByUserId(userId);
    }

    @Override
    public UserAddress createAddress(UserAddress address) {
        return userAddressDAO.create(address);
    }

    @Override
    public UserAddress updateAddress(UserAddress address) {
        return userAddressDAO.update(address);
    }

    @Override
    public boolean deleteAddress(Integer id) {
        return userAddressDAO.delete(id);
    }
}
