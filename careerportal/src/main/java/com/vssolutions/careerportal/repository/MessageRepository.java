package com.vssolutions.careerportal.repository;

import com.vssolutions.careerportal.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Full 2-way conversation between two specific people, oldest first
    @Query("SELECT m FROM Message m WHERE " +
           "(m.senderId = :userA AND m.senderType = :typeA AND m.receiverId = :userB AND m.receiverType = :typeB) OR " +
           "(m.senderId = :userB AND m.senderType = :typeB AND m.receiverId = :userA AND m.receiverType = :typeA) " +
           "ORDER BY m.sentAt ASC")
    List<Message> findConversation(@Param("userA") Long userA, @Param("typeA") String typeA,
                                    @Param("userB") Long userB, @Param("typeB") String typeB);

    // All messages where this user is either sender or receiver, newest first (used to build the inbox list)
    @Query("SELECT m FROM Message m WHERE " +
           "(m.senderId = :userId AND m.senderType = :userType) OR " +
           "(m.receiverId = :userId AND m.receiverType = :userType) " +
           "ORDER BY m.sentAt DESC")
    List<Message> findAllForUser(@Param("userId") Long userId, @Param("userType") String userType);

    long countByReceiverIdAndReceiverTypeAndIsReadFalse(Long receiverId, String receiverType);
}
