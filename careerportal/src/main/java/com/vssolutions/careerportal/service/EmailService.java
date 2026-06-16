package com.vssolutions.careerportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private void sendEmail(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
            System.out.println("Email sent to " + to);
        } catch (MessagingException e) {
            System.err.println("Email send failed: " + e.getMessage());
        }
    }

    // 1. Welcome Email
    public void sendWelcomeEmail(String to, String name) {
        String html = """
            <div style="font-family:Arial,sans-serif;max-width:600px;margin:auto;padding:20px;border:1px solid #e0e0e0;border-radius:8px;">
              <div style="background-color:#0073e6;padding:20px;border-radius:8px 8px 0 0;text-align:center;">
                <h1 style="color:white;margin:0;">VS-Solutions</h1>
                <p style="color:#cce5ff;">Career Portal</p>
              </div>
              <div style="padding:30px;">
                <h2>Welcome, %s!</h2>
                <p>You have successfully registered on the VS-Solutions Career Portal!</p>
                <ul>
                  <li>Search and explore job opportunities</li>
                  <li>Upload your resume</li>
                  <li>Apply for jobs</li>
                  <li>Track your application status</li>
                </ul>
                <div style="text-align:center;margin:30px 0;">
                  <a href="http://localhost:8080" style="background-color:#0073e6;color:white;padding:12px 30px;border-radius:5px;text-decoration:none;">Visit Portal</a>
                </div>
              </div>
              <div style="background-color:#f5f5f5;padding:15px;text-align:center;">
                <p style="color:#999;font-size:12px;">2026 VS-Solutions. All rights reserved.</p>
              </div>
            </div>
        """.formatted(name);
        sendEmail(to, "Welcome to VS-Solutions Career Portal!", html);
    }

    // 2. Application Submitted
    public void sendApplicationEmail(String to, String name, String jobTitle, String company) {
        String html = """
            <div style="font-family:Arial,sans-serif;max-width:600px;margin:auto;padding:20px;border:1px solid #e0e0e0;border-radius:8px;">
              <div style="background-color:#0073e6;padding:20px;border-radius:8px 8px 0 0;text-align:center;">
                <h1 style="color:white;margin:0;">VS-Solutions</h1>
                <p style="color:#cce5ff;">Career Portal</p>
              </div>
              <div style="padding:30px;">
                <h2>Application Received!</h2>
                <p>Hi <strong>%s</strong>,</p>
                <p>Your application has been successfully submitted.</p>
                <div style="background-color:#f0f7ff;border-left:4px solid #0073e6;padding:15px;margin:20px 0;">
                  <p><strong>Job Title:</strong> %s</p>
                  <p><strong>Company:</strong> %s</p>
                </div>
                <div style="text-align:center;margin:30px 0;">
                  <a href="http://localhost:8080/applications" style="background-color:#0073e6;color:white;padding:12px 30px;border-radius:5px;text-decoration:none;">Track Application</a>
                </div>
              </div>
              <div style="background-color:#f5f5f5;padding:15px;text-align:center;">
                <p style="color:#999;font-size:12px;">2026 VS-Solutions. All rights reserved.</p>
              </div>
            </div>
        """.formatted(name, jobTitle, company);
        sendEmail(to, "Application Submitted - " + jobTitle, html);
    }

    // 3. Interview Scheduled
    public void sendInterviewEmail(String to, String name, String jobTitle,
                                   String date, String time, String mode, String link) {
        String html = """
            <div style="font-family:Arial,sans-serif;max-width:600px;margin:auto;padding:20px;border:1px solid #e0e0e0;border-radius:8px;">
              <div style="background-color:#0073e6;padding:20px;border-radius:8px 8px 0 0;text-align:center;">
                <h1 style="color:white;margin:0;">VS-Solutions</h1>
                <p style="color:#cce5ff;">Career Portal</p>
              </div>
              <div style="padding:30px;">
                <h2>Interview Scheduled!</h2>
                <p>Hi <strong>%s</strong>,</p>
                <p>Your interview has been scheduled.</p>
                <div style="background-color:#f0f7ff;border-left:4px solid #0073e6;padding:15px;margin:20px 0;">
                  <p><strong>Job Title:</strong> %s</p>
                  <p><strong>Date:</strong> %s</p>
                  <p><strong>Time:</strong> %s</p>
                  <p><strong>Mode:</strong> %s</p>
                  <p><strong>Join Link:</strong> <a href="%s">%s</a></p>
                </div>
                <div style="text-align:center;margin:30px 0;">
                  <a href="http://localhost:8080/interviews" style="background-color:#0073e6;color:white;padding:12px 30px;border-radius:5px;text-decoration:none;">View Details</a>
                </div>
              </div>
              <div style="background-color:#f5f5f5;padding:15px;text-align:center;">
                <p style="color:#999;font-size:12px;">2026 VS-Solutions. All rights reserved.</p>
              </div>
            </div>
        """.formatted(name, jobTitle, date, time, mode, link, link);
        sendEmail(to, "Interview Scheduled - " + jobTitle, html);
    }

    // 4. Status Update
    public void sendStatusUpdateEmail(String to, String name, String jobTitle, String status) {
        String color = switch (status) {
            case "Under Review"        -> "#f39c12";
            case "Interview Scheduled" -> "#3498db";
            case "Selected"            -> "#27ae60";
            case "Rejected"            -> "#e74c3c";
            default                    -> "#333";
        };
        String html = """
            <div style="font-family:Arial,sans-serif;max-width:600px;margin:auto;padding:20px;border:1px solid #e0e0e0;border-radius:8px;">
              <div style="background-color:#0073e6;padding:20px;border-radius:8px 8px 0 0;text-align:center;">
                <h1 style="color:white;margin:0;">VS-Solutions</h1>
                <p style="color:#cce5ff;">Career Portal</p>
              </div>
              <div style="padding:30px;">
                <h2>Application Status Updated!</h2>
                <p>Hi <strong>%s</strong>,</p>
                <p>Your application status has been updated.</p>
                <div style="background-color:#f0f7ff;border-left:4px solid #0073e6;padding:15px;margin:20px 0;">
                  <p><strong>Job Title:</strong> %s</p>
                  <p><strong>New Status:</strong> <span style="color:%s;font-weight:bold;">%s</span></p>
                </div>
                <div style="text-align:center;margin:30px 0;">
                  <a href="http://localhost:8080/applications" style="background-color:#0073e6;color:white;padding:12px 30px;border-radius:5px;text-decoration:none;">View Application</a>
                </div>
              </div>
              <div style="background-color:#f5f5f5;padding:15px;text-align:center;">
                <p style="color:#999;font-size:12px;">2026 VS-Solutions. All rights reserved.</p>
              </div>
            </div>
        """.formatted(name, jobTitle, color, status);
        sendEmail(to, "Application Update - " + jobTitle, html);
    }
}