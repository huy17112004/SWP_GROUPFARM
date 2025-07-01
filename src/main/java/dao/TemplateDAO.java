
package dao;

import entity.ContractTemplate;
import jakarta.persistence.*;

public class TemplateDAO extends GenericDAO<ContractTemplate> {
    public TemplateDAO(EntityManager em) {
        super(ContractTemplate.class, em);
    }

    public ContractTemplate findByName(String name) {
        try {
            return em.createQuery(
                            "SELECT t FROM ContractTemplate t WHERE t.name = :nm",
                            ContractTemplate.class)
                    .setParameter("nm", name)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
