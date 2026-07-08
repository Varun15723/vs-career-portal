package com.vssolutions.careerportal.service;

import com.vssolutions.careerportal.model.Message;
import com.vssolutions.careerportal.repository.CandidateRepository;
import com.vssolutions.careerportal.repository.MessageRepository;
import com.vssolutions.careerportal.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    public Message sendMessage(Long senderId, String senderType, Long receiverId, String receiverType, String content) {
        if (content == null || content.isBlank()) {
            throw new RuntimeException("Message content cannot be empty");
        }
        boolean receiverExists = "candidate".equalsIgnoreCase(receiverType)
            ? candidateRepository.existsById(receiverId)
            : recruiterRepository.existsById(receiverId);
        if (!receiverExists) {
            throw new RuntimeException("Recipient not found");
        }

        Message message = new Message();
        message.setSenderId(senderId);
        message.setSenderType(senderType);
        message.setReceiverId(receiverId);
        message.setReceiverType(receiverType);
        message.setContent(content);
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long userA, String typeA, Long userB, String typeB) {
        return messageRepository.findConversation(userA, typeA, userB, typeB);
    }

    public Message markAsRead(Long id) {
        Message message = messageRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Message not found"));
        message.setIsRead(true);
        return messageRepository.save(message);
    }

    public long getUnreadCount(Long userId, String userType) {
        return messageRepository.countByReceiverIdAndReceiverTypeAndIsReadFalse(userId, userType);
    }

    // Builds a simplified inbox: one row per distinct correspondent, with their
    // display name and the most recent message in that thread.
    public List<Map<String, Object>> getInbox(Long userId, String userType) {
        List<Message> all = messageRepository.findAllForUser(userId, userType);

        Map<String, Message> latestPerThread = new LinkedHashMap<>();
        for (Message m : all) {
            boolean isSender = m.getSenderId().equals(userId) && m.getSenderType().equalsIgnoreCase(userType);
            Long otherId = isSender ? m.getReceiverId() : m.getSenderId();
            String otherType = isSender ? m.getReceiverType() : m.getSenderType();
            String key = otherType + ":" + otherId;
            latestPerThread.putIfAbsent(key, m); // list is already newest-first
        }

        List<Map<String, Object>> inbox = new ArrayList<>();
        for (Map.Entry<String, Message> entry : latestPerThread.entrySet()) {
            String[] parts = entry.getKey().split(":");
            String otherType = parts[0];
            Long otherId = Long.parseLong(parts[1]);
            Message lastMessage = entry.getValue();

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("otherUserId", otherId);
            row.put("otherUserType", otherType);
            row.put("otherUserName", resolveName(otherId, otherType));
            row.put("lastMessage", lastMessage.getContent());
            row.put("lastMessageTime", lastMessage.getSentAt());
            row.put("unread", !lastMessage.getIsRead() &&
                lastMessage.getReceiverId().equals(userId) && lastMessage.getReceiverType().equalsIgnoreCase(userType));
            inbox.add(row);
        }
        return inbox;
    }

    private String resolveName(Long id, String type) {
        if ("candidate".equalsIgnoreCase(type)) {
            return candidateRepository.findById(id).map(c -> c.getFullName()).orElse("Unknown");
        } else {
            return recruiterRepository.findById(id).map(r -> r.getName()).orElse("Unknown");
        }
    }
}
