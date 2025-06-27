package service;

import dao.*;
import dto.ChatMessageDTO;
import dto.MessageRequestDTO;
import entity.*;
import jakarta.persistence.EntityManager;
import util.JpaUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    public ChatMessageDTO saveMessage(int senderId, MessageRequestDTO dto) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            MessageDAO messageDAO = new MessageDAO(em);
            WholesaleOrderDAO wholesaleOrderDAO = new WholesaleOrderDAO(em);

            WholesaleOrder order = wholesaleOrderDAO.findById(dto.getOrderId());

            if (senderId != order.getCustomer().getId() && senderId != order.getSeller().getId()) {
                throw new IllegalArgumentException("Sender does not belong to this order");
            }

            String senderType = (senderId == order.getCustomer().getId()) ? "CUSTOMER" : "SELLER";

            WholesaleCustomer customer = order.getCustomer();

            Seller seller = order.getSeller();

            Message message = new Message();
            message.setContent(dto.getContent());
            message.setCreatedAt(LocalDateTime.now());
            message.setSenderType(senderType);
            message.setCustomer(customer);
            message.setSeller(seller);
            message.setOrder(order);

            messageDAO.save(message);
            em.getTransaction().commit();

            ChatMessageDTO chatDto = new ChatMessageDTO();
            chatDto.setId(message.getId());
            chatDto.setContent(message.getContent());
            chatDto.setCreatedAt(message.getCreatedAt());
            chatDto.setSenderType(message.getSenderType());
            chatDto.setOrderId(order.getId());
            chatDto.setSenderName(message.getSenderType().equals("CUSTOMER")
                    ? customer.getContactPerson()
                    : seller.getName());
            chatDto.setSender(true);

            return chatDto;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<ChatMessageDTO> getMessagesByOrderId(int senderId, int orderId) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            MessageDAO messageDAO = new MessageDAO(em);
            AccountDAO accountDAO = new AccountDAO(em);
            List<Message> messages = messageDAO.findByOrderId(orderId);
            List<ChatMessageDTO> dtoList = new ArrayList<>();
            for (Message m : messages) {
                ChatMessageDTO dto = new ChatMessageDTO();
                dto.setId(m.getId());
                dto.setContent(m.getContent());
                dto.setCreatedAt(m.getCreatedAt());
                dto.setSenderType(m.getSenderType());
                dto.setOrderId(orderId);

                if ("CUSTOMER".equals(m.getSenderType())) {
                    if (senderId == m.getCustomer().getId()) {
                        dto.setSender(true);
                    }
                    dto.setSenderName(m.getCustomer().getContactPerson());
                } else {
                    if (senderId == m.getSeller().getId()) {
                        dto.setSender(true);
                    }
                    dto.setSenderName(m.getSeller().getName());
                }
                dtoList.add(dto);
            }

            return dtoList;
        } finally {
            em.close();
        }
    }

    public List<ChatMessageDTO> getMessagesByOrderIdSince(int senderId, int orderId, LocalDateTime since) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            MessageDAO messageDAO = new MessageDAO(em);
            AccountDAO accountDAO = new AccountDAO(em);
            List<Message> messages = messageDAO.findByOrderIdAndCreatedAfter(orderId, since);
            List<ChatMessageDTO> dtoList = new ArrayList<>();
            for (Message m : messages) {
                ChatMessageDTO dto = new ChatMessageDTO();
                dto.setId(m.getId());
                dto.setContent(m.getContent());
                dto.setCreatedAt(m.getCreatedAt());
                dto.setSenderType(m.getSenderType());
                dto.setOrderId(orderId);

                if ("CUSTOMER".equals(m.getSenderType())) {
                    if (senderId == m.getCustomer().getId()) {
                        dto.setSender(true);
                    }
                    dto.setSenderName(m.getCustomer().getContactPerson());
                } else {
                    if (senderId == m.getSeller().getId()) {
                        dto.setSender(true);
                    }
                    dto.setSenderName(m.getSeller().getName());
                }
                dtoList.add(dto);
            }

            return dtoList;
        } finally {
            em.close();
        }
    }
}
