package com.nextcart.serviceImpl;

import com.nextcart.dao.*;
import com.nextcart.daoImpl.*;
import com.nextcart.model.*;
import com.nextcart.service.FlashSaleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlashSaleServiceImpl implements FlashSaleService {
    private final FlashSaleDAO flashSaleDAO = new FlashSaleDAOImpl();
    private final FlashSaleProductDAO flashSaleProductDAO = new FlashSaleProductDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Optional<FlashSale> getFlashSaleById(Integer flashSaleId) {
        Optional<FlashSale> flashSaleOpt = flashSaleDAO.findById(flashSaleId);
        if (flashSaleOpt.isPresent()) {
            populateFlashSaleProducts(flashSaleOpt.get());
        }
        return flashSaleOpt;
    }

    @Override
    public List<FlashSale> getAllFlashSales() {
        List<FlashSale> flashSales = flashSaleDAO.findAll();
        for (FlashSale flashSale : flashSales) {
            populateFlashSaleProducts(flashSale);
        }
        return flashSales;
    }

    @Override
    public List<FlashSale> getActiveFlashSales() {
        List<FlashSale> flashSales = flashSaleDAO.findActive();
        for (FlashSale flashSale : flashSales) {
            populateFlashSaleProducts(flashSale);
        }
        return flashSales;
    }

    @Override
    public FlashSale createFlashSale(FlashSale flashSale) {
        return flashSaleDAO.create(flashSale);
    }

    @Override
    public FlashSale updateFlashSale(FlashSale flashSale) {
        return flashSaleDAO.update(flashSale);
    }

    @Override
    public void deleteFlashSale(Integer flashSaleId) {
        flashSaleProductDAO.deleteByFlashSaleId(flashSaleId);
        flashSaleDAO.delete(flashSaleId);
    }

    private void populateFlashSaleProducts(FlashSale flashSale) {
        List<FlashSaleProduct> fspList = flashSaleProductDAO.findByFlashSaleId(flashSale.getFlashSaleId());
        List<Product> products = new ArrayList<>();
        for (FlashSaleProduct fsp : fspList) {
            Optional<Product> productOpt = productDAO.findById(fsp.getProductId());
            productOpt.ifPresent(products::add);
        }
        flashSale.setProducts(products);
    }
}
