package com.project.assist_tracker.controller;

import com.project.assist_tracker.model.HelpLog;
import com.project.assist_tracker.repository.HelpLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin
public class HelpLogController {

    @Autowired
    private HelpLogRepository repo;

    @GetMapping
    public List<HelpLog> getAll() { return repo.findAll(); }

    @PostMapping
    public HelpLog create(@RequestBody HelpLog log) { return repo.save(log); }

    @PutMapping("/{id}")
    public HelpLog update(@PathVariable Long id, @RequestBody HelpLog updatedLog) {
        HelpLog log = repo.findById(id).orElseThrow();
        log.setHelperName(updatedLog.getHelperName());
        log.setHelpedTo(updatedLog.getHelpedTo());
        log.setIssueType(updatedLog.getIssueType());
        log.setDescription(updatedLog.getDescription());
        log.setTimeSpentMinutes(updatedLog.getTimeSpentMinutes());
        return repo.save(log);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}