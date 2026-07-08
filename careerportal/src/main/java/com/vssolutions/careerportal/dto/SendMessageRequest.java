package com.vssolutions.careerportal.dto;

public class SendMessageRequest {
    private Long receiverId;
    private String receiverType; // "candidate" or "recruiter"
    private String content;

    public Long getReceiverId() { return receiverId; }
    public void setReceiverId(Long receiverId) { this.receiverId = receiverId; }
    public String getReceiverType() { return receiverType; }
    public void setReceiverType(String receiverType) { this.receiverType = receiverType; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
