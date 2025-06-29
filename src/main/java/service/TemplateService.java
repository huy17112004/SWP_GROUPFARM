// service/TemplateService.java
package service;

import entity.ContractTemplate;
import jakarta.persistence.*;
import dao.TemplateDAO;
import util.JpaUtil;

import java.time.LocalDateTime;
import java.util.Date;

public class TemplateService {
    public ContractTemplate loadTemplate(String name) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return new TemplateDAO(em).findByName(name);
        } finally {
            em.close();
        }
    }

    public void saveOrUpdate(String name, String html) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            TemplateDAO  dao = new TemplateDAO(em);
            ContractTemplate tpl = dao.findByName(name);
            if (tpl == null) {
                tpl = new ContractTemplate(name, html);
                dao.save(tpl);
            } else {
                tpl.setContentHtml(html);
                dao.update(tpl);
            }
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) tx.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
