package dao;

import entity.ShippingRequirement;
import entity.StockLot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StockLotDAO extends GenericDAO<StockLot> {
    public StockLotDAO(EntityManager em) {
        super(StockLot.class, em);
    }
}
