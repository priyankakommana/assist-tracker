package com.project.assist_tracker.controller;
import com.project.assist_tracker.model.User;
import com.project.assist_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired private UserRepository userRepo;

    @GetMapping("/test")
    public String test(){ return "Auth API Working! Users count: "+userRepo.count(); }

    @PostMapping("/init")
    public Map<String,Object> init() {
        Map<String,Object> res=new HashMap<>();
        try{
            if(userRepo.count()==0){
                userRepo.save(new User("devi","devi123","USER"));
                userRepo.save(new User("piyanka","piyu123","USER"));
                userRepo.save(new User("sayan","sayan123","USER"));
                userRepo.save(new User("avinash","avi123","USER"));
                userRepo.save(new User("admin","admin123","ADMIN"));
                res.put("msg","5 Users Created Successfully!");
            } else {
                res.put("msg","Users already exist: "+userRepo.count());
            }
            res.put("success",true);
            res.put("users",userRepo.findAll());
        } catch(Exception e){
            res.put("success",false);
            res.put("error",e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody Map<String,String> body){
        Map<String,Object> res=new HashMap<>();
        String u=body.get("username").toLowerCase();
        String p=body.get("password");
        String r=body.getOrDefault("role","USER");
        if(userRepo.findByUsername(u).isPresent()){
            res.put("success",false); res.put("msg","User already exists");
        } else {
            userRepo.save(new User(u,p,r));
            res.put("success",true); res.put("msg","User "+u+" created");
        }
        return res;
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> body){
        String u = body.get("username").toLowerCase();
        String p = body.get("password");
        String pin = body.get("pin");
        Optional<User> opt = userRepo.findByUsername(u);
        Map<String,Object> res = new HashMap<>();
        if(opt.isPresent() && opt.get().getPassword().equals(p)){
            User user = opt.get();
            if(user.getRole().equals("ADMIN")){
                if(!"9999".equals(pin)){
                    res.put("success",false); res.put("msg","Wrong Secret PIN! Enter 9999");
                    return res;
                }
            }
            res.put("success",true);
            res.put("username",user.getUsername());
            res.put("role",user.getRole());
        } else {
            res.put("success",false); res.put("msg","Invalid Username/Password");
        }
        return res;
    }
}