# Assist Tracker - Access Request & Help Desk System

**GitHub:** https://github.com/priyankakommana/assist-tracker
**Live Demo (Deployed on AWS EC2 with Docker & Ansible):** http://13.203.67.162:8080
- Admin Login: admin / admin

### Tech Stack
Spring Boot, React, MySQL, Docker, Ansible, AWS EC2, JavaMail

### Features
- Users request access, Admin manages Pending Requests (Approve/Reject)
- Help Desk logging with Email notifications
- Fully Dockerized & Auto-deployed via Ansible

### How to Run
docker-compose up --build

# Assist Tracker - Access Request & Help Desk System

Live Demo: http://13.203.67.162 (EC2 Stopped to save cost, Start in 30sec)

## Tech Stack
Spring Boot, React, MySQL, Docker, Ansible, AWS EC2, JavaMail

## Features
- User can raise access requests & help desk tickets
- Admin can approve/reject in Pending Requests (auto email sent)
- RBAC - Admin/User roles
- Dockerized & automated deployment via Ansible

## How to Run (2 commands)
docker-compose up -d

## DevOps
- Deployed on AWS EC2 t2.micro (Free Tier - $100 credit safe)
- Zero-touch deployment with Ansible (ok=8)
- Cost optimization: Stopped when not in use
