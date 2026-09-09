package com.project.assist_tracker.controller;

import com.project.assist_tracker.model.AccessRequest;
import com.project.assist_tracker.model.User;
import com.project.assist_tracker.repository.AccessRequestRepository;
import com.project.assist_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/requests")
public class AccessRequestController {

    @Autowired private AccessRequestRepository reqRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private JavaMailSender mailSender;

    // @PostMapping
    // public Map<String,Object> create(@RequestBody AccessRequest req) {
    //     System.out.println("New request from: " + req.getRequestedUsername());
        
    //     // Save request to DB - ee code unte unchay
    //     // ... nee existing save logic ...
    //     //     public Map<String,Object> create(@RequestBody AccessRequest req) {
        
    //     // System.out.println("New request from: " + req.getRequestedUsername());
    //     // DB save logic - nee repository.save(req) unda chudu

    //     // Mail background lo
    //     try {
    //         SimpleMailMessage msg = new SimpleMailMessage();
    //         msg.setTo("priyanka.sekhar.kommana@gmail.com");
    //         msg.setSubject("New Access Request - " + req.getRequestedUsername());
    //         msg.setText("Hi Priyanka,\n\nNew request: " + req.getFullName() + 
    //                     "\nUsername: " + req.getRequestedUsername() +
    //                     "\nEmail: " + req.getEmail() +
    //                     "\nReason: " + req.getReason());
            
    //         new Thread(() -> {
    //             try { mailSender.send(msg); System.out.println("Mail SENT!"); } 
    //             catch(Exception e){ System.out.println("Mail FAILED: "+e.getMessage()); }
    //         }).start();
            
    //     } catch(Exception e){ System.out.println("Mail prep error: "+e.getMessage()); }

    //     return Map.of("success",true,"msg","Request sent! Manager notified");
    // }
    //     // return Map.of("success",true,"msg","Request sent! Manager notified at priyanka.sekhar.kommana@gmail.com");
    //     // if(userRepo.findByUsername(req.getRequestedUsername().toLowerCase()).isPresent()){
    //     //     return Map.of("success",false,"msg","Username already exists!");
    //     // }
    //     // req.setStatus("PENDING");
    //     // reqRepo.save(req);

    //     // try {
    //     //     SimpleMailMessage msg = new SimpleMailMessage();
    //     //     msg.setTo("priyanka.sekhar.kommana@gmail.com"); // Manager Mail
    //     //     msg.setSubject("New Access Request - " + req.getRequestedUsername());
    //     //     msg.setText("Hi Priyanka,\n\nNew access request received:\n\nName: " + req.getFullName() + 
    //     //                 "\nUsername: " + req.getRequestedUsername() + 
    //     //                 "\nEmail: " + req.getEmail() + 
    //     //                 "\nReason: " + req.getReason() + 
    //     //                 "\n\nPlease login to Admin Dashboard to Approve.\n\n- AssistSync");
    //     //     // Mail background lo pampistunnam - app hang avvadhu
    //     //     new Thread(() -> {
    //     //         try {
    //     //             mailSender.send(msg);
    //     //             System.out.println("Mail sent successfully!");
    //     //         } catch (Exception e) {
    //     //             System.out.println("Mail failed: " + e.getMessage());
    //     //             e.printStackTrace();
    //     //         }
    //     //     }).start();
    //     //     System.out.println("Mail sent to priyanka.sekhar.kommana@gmail.com");
    //     // } catch (Exception e) {
    //     //     System.out.println("Mail Error: " + e.getMessage());
    //     // }
    //     // return Map.of("success",true,"msg","Request sent! Manager notified at priyanka.sekhar.kommana@gmail.com");
    

//     @PostMapping
// public Map<String,Object> create(@RequestBody AccessRequest req) {
//     System.out.println("New request from: " + req.getRequestedUsername());
    
//     if(userRepo.findByUsername(req.getRequestedUsername().toLowerCase()).isPresent()){
//         return Map.of("success",false,"msg","Username already exists!");
//     }
//     req.setStatus("PENDING");
//     reqRepo.save(req);  // <-- IDHI LEKAPOTHE SAVE AVVADU BRO!

//     try {
//         SimpleMailMessage msg = new SimpleMailMessage();
//         msg.setTo("priyanka.sekhar.kommana@gmail.com");
//         msg.setSubject("New Access Request - " + req.getRequestedUsername());
//         msg.setText("Hi Priyanka,\n\nNew request: " + req.getFullName() + 
//                     "\nUsername: " + req.getRequestedUsername() +
//                     "\nEmail: " + req.getEmail() +
//                     "\nReason: " + req.getReason());
        
//         new Thread(() -> {
//             try { mailSender.send(msg); System.out.println("Mail SENT!"); } 
//             catch(Exception e){ System.out.println("Mail FAILED: "+e.getMessage()); }
//         }).start();
        
//     } catch(Exception e){ System.out.println("Mail prep error: "+e.getMessage()); }

//     return Map.of("success",true,"msg","Request sent! Manager notified");
// }
// @PostMapping
// public Map<String,Object> create(@RequestBody AccessRequest req) {
//     System.out.println("New request from: " + req.getRequestedUsername());
    
//     if(userRepo.findByUsername(req.getRequestedUsername().toLowerCase()).isPresent()){
//         return Map.of("success",false,"msg","Username already exists! Use different name");
//     }
//     req.setStatus("PENDING");
//     reqRepo.save(req);  // IDHI MUST!

//     System.out.println("Saved to DB successfully!");
//     return Map.of("success",true,"msg","Request sent! Manager notified");
// }
@PostMapping
public Map<String,Object> create(@RequestBody AccessRequest req) {
    if(userRepo.findByUsername(req.getRequestedUsername().toLowerCase()).isPresent()){
        return Map.of("success",false,"msg","Username already exists!");
    }
    req.setStatus("PENDING");
    reqRepo.save(req);

    // Mail background lo - API hang avvadu
    try {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("priyanka.sekhar.kommana@gmail.com");
        msg.setFrom("priyanka.sekhar.kommana@gmail.com");
        msg.setSubject("New Access Request - " + req.getRequestedUsername());
        msg.setText("Hi Priyanka,\n\nName: " + req.getFullName() + 
                    "\nUsername: " + req.getRequestedUsername() +
                    "\nEmail: " + req.getEmail() +
                    "\nReason: " + req.getReason());

        new Thread(() -> {
            try { 
                mailSender.send(msg); 
                System.out.println("Mail SENT! to Priyanka");
            } catch(Exception e){ 
                System.out.println("Mail FAILED: "+e.getMessage());
                e.printStackTrace();
            }
        }).start();
    } catch(Exception e){ System.out.println("Mail prep error: "+e.getMessage()); }

    return Map.of("success",true,"msg","Request sent! Manager notified");
}
    @GetMapping
    
    public List<AccessRequest> getAll() { return reqRepo.findAll(); }

    @PostMapping("/{id}/approve")
    public Map<String,Object> approve(@PathVariable Long id, @RequestBody(required=false) Map<String,String> body) {
        var opt = reqRepo.findById(id);
        if(opt.isEmpty()) return Map.of("success",false,"msg","Request not found");
        AccessRequest req = opt.get();
        String pass = (body != null && body.get("password") != null && !body.get("password").isBlank()) ? body.get("password") : req.getRequestedUsername() + "123";
        User u = new User();
        u.setUsername(req.getRequestedUsername().toLowerCase());
        u.setPassword(pass);
        u.setRole("USER");
        userRepo.save(u);
        req.setStatus("APPROVED");
        reqRepo.save(req);
        return Map.of("success",true,"msg","User " + req.getRequestedUsername() + " created! Password: " + pass);
    }
}