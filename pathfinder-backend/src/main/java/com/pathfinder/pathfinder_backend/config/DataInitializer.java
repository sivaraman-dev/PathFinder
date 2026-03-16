package com.pathfinder.pathfinder_backend.config;

import com.pathfinder.pathfinder_backend.model.Career;
import com.pathfinder.pathfinder_backend.model.Question;
import com.pathfinder.pathfinder_backend.repository.CareerRepository;
import com.pathfinder.pathfinder_backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CareerRepository careerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public void run(String... args) {
        initializeCareers();
        initializeQuestions();
    }

    private void initializeCareers() {
        if (careerRepository.count() == 0) {
            List<Career> careers = buildAllCareers();
            careerRepository.saveAll(careers);
        }
    }

    private static Career c(String title, String desc, String edu, String salary, String growth,
                           Integer logical, Integer verbal, Integer numerical, Integer creative,
                           String[] skills, String[] interests) {
        Career career = new Career();
        career.setTitle(title);
        career.setDescription(desc);
        career.setEducationPath(edu);
        career.setSalaryRange(salary);
        career.setGrowthProspects(growth);
        if (logical != null) career.setMinLogicalScore(logical);
        if (verbal != null) career.setMinVerbalScore(verbal);
        if (numerical != null) career.setMinNumericalScore(numerical);
        if (creative != null) career.setMinCreativeScore(creative);
        career.setRequiredSkills(Arrays.asList(skills));
        career.setRelatedInterests(Arrays.asList(interests));
        return career;
    }

    private static List<Career> buildAllCareers() {
        List<Career> list = new ArrayList<>();
        // 1–19: Original + previous batch
        list.add(c("Software Developer", "Design, develop, and maintain software applications", "Bachelor's in Computer Science or related field", "₹6-25 LPA", "Excellent", 70, null, 65, null, new String[]{"Programming", "Problem Solving", "Algorithms"}, new String[]{"Technology", "Coding", "Innovation"}));
        list.add(c("Data Scientist", "Analyze complex data to help companies make better decisions", "Bachelor's in Statistics/Mathematics/CS + Masters preferred", "₹8-30 LPA", "Excellent", 80, null, 85, null, new String[]{"Statistics", "Machine Learning", "Python/R"}, new String[]{"Data Analysis", "Mathematics", "Technology"}));
        list.add(c("Graphic Designer", "Create visual content to communicate messages", "Bachelor's in Design/Fine Arts", "₹3-15 LPA", "Good", null, 60, null, 80, new String[]{"Creativity", "Design Software", "Color Theory"}, new String[]{"Art", "Design", "Creativity"}));
        list.add(c("Doctor", "Diagnose and treat illnesses, provide medical care", "MBBS + MD/MS specialization", "₹8-50 LPA", "Excellent", 75, 70, 75, null, new String[]{"Medical Knowledge", "Empathy", "Decision Making"}, new String[]{"Healthcare", "Biology", "Helping Others"}));
        list.add(c("Teacher", "Educate and inspire students", "Bachelor's + B.Ed", "₹3-12 LPA", "Good", null, 75, null, 60, new String[]{"Communication", "Patience", "Subject Knowledge"}, new String[]{"Education", "Mentoring", "Knowledge Sharing"}));
        list.add(c("Mechanical Engineer", "Design and develop mechanical systems and machines", "Bachelor's in Mechanical Engineering", "₹4-20 LPA", "Good", 72, null, 70, null, new String[]{"CAD", "Thermodynamics", "Problem Solving"}, new String[]{"Machines", "Technology", "Innovation"}));
        list.add(c("Lawyer", "Advise clients on legal matters and represent them in court", "LLB / LLM", "₹5-30 LPA", "Good", 75, 80, null, null, new String[]{"Research", "Argumentation", "Legal Knowledge"}, new String[]{"Law", "Justice", "Debate"}));
        list.add(c("Chartered Accountant", "Manage finances, audit, and provide tax advisory", "CA Foundation + Intermediate + Final", "₹6-25 LPA", "Excellent", 70, null, 85, null, new String[]{"Accounting", "Taxation", "Analytics"}, new String[]{"Finance", "Numbers", "Business"}));
        list.add(c("Digital Marketing Specialist", "Run online campaigns and grow brand presence", "Bachelor's + Marketing certifications", "₹3-18 LPA", "Excellent", null, 65, null, 70, new String[]{"SEO", "Content", "Analytics"}, new String[]{"Marketing", "Creativity", "Technology"}));
        list.add(c("Content Writer", "Create articles, blogs, and copy for brands", "Bachelor's in English/Journalism/Mass Communication", "₹2-12 LPA", "Good", null, 78, null, 72, new String[]{"Writing", "Research", "SEO"}, new String[]{"Writing", "Reading", "Creativity"}));
        list.add(c("UX Designer", "Design user-friendly digital products and experiences", "Bachelor's in Design/HCI or related", "₹5-22 LPA", "Excellent", 68, null, null, 78, new String[]{"User Research", "Wireframing", "Prototyping"}, new String[]{"Design", "Technology", "User Experience"}));
        list.add(c("Cybersecurity Analyst", "Protect systems and data from cyber threats", "Bachelor's in CS/IT + security certifications", "₹6-28 LPA", "Excellent", 78, null, 70, null, new String[]{"Networking", "Ethical Hacking", "Risk Analysis"}, new String[]{"Technology", "Security", "Problem Solving"}));
        list.add(c("Product Manager", "Define product vision and work with teams to build it", "Bachelor's + MBA or product certifications", "₹10-35 LPA", "Excellent", 72, 72, null, null, new String[]{"Prioritization", "Stakeholder Management", "Analytics"}, new String[]{"Technology", "Business", "Innovation"}));
        list.add(c("Research Scientist", "Conduct experiments and advance scientific knowledge", "PhD or Master's in relevant science", "₹6-25 LPA", "Good", 82, null, 80, null, new String[]{"Research Methods", "Analysis", "Writing"}, new String[]{"Science", "Discovery", "Mathematics"}));
        list.add(c("Psychologist", "Study behavior and help people with mental well-being", "Master's/PhD in Psychology", "₹4-20 LPA", "Good", 68, 78, null, null, new String[]{"Empathy", "Analysis", "Communication"}, new String[]{"Helping Others", "Human Behavior", "Research"}));
        list.add(c("Architect", "Design buildings and oversee construction", "Bachelor's in Architecture (B.Arch)", "₹5-25 LPA", "Good", null, null, 65, 82, new String[]{"Design", "CAD", "Spatial Thinking"}, new String[]{"Design", "Art", "Buildings"}));
        list.add(c("Civil Engineer", "Plan and supervise infrastructure projects", "Bachelor's in Civil Engineering", "₹4-18 LPA", "Good", 70, null, 72, null, new String[]{"Structural Design", "Project Management", "Standards"}, new String[]{"Infrastructure", "Construction", "Problem Solving"}));
        list.add(c("Nurse", "Provide patient care and support in healthcare settings", "B.Sc Nursing or GNM", "₹3-12 LPA", "Good", 62, 70, null, null, new String[]{"Patient Care", "Communication", "Compassion"}, new String[]{"Healthcare", "Helping Others", "Biology"}));
        list.add(c("Financial Analyst", "Analyze financial data and guide investment decisions", "Bachelor's in Finance/Economics + CFA preferred", "₹5-25 LPA", "Excellent", 75, null, 82, null, new String[]{"Financial Modeling", "Excel", "Analysis"}, new String[]{"Finance", "Data Analysis", "Business"}));
        // 20–50
        list.add(c("DevOps Engineer", "Automate and manage CI/CD and cloud infrastructure", "Bachelor's in CS/IT + DevOps certifications", "₹8-28 LPA", "Excellent", 75, null, 68, null, new String[]{"Linux", "Docker", "Kubernetes", "AWS"}, new String[]{"Technology", "Automation", "Infrastructure"}));
        list.add(c("Game Developer", "Create video games and interactive experiences", "Bachelor's in Game Dev/CS", "₹4-22 LPA", "Excellent", 72, null, 65, 75, new String[]{"Unity", "C#", "Game Design"}, new String[]{"Gaming", "Technology", "Creativity"}));
        list.add(c("AI/ML Engineer", "Build and deploy machine learning models", "Bachelor's/Master's in CS/Math/Stats", "₹10-35 LPA", "Excellent", 82, null, 80, null, new String[]{"Python", "TensorFlow", "ML Algorithms"}, new String[]{"Technology", "Mathematics", "Innovation"}));
        list.add(c("Blockchain Developer", "Develop decentralized applications and smart contracts", "Bachelor's in CS + blockchain knowledge", "₹8-30 LPA", "Excellent", 78, null, 72, null, new String[]{"Solidity", "Smart Contracts", "Cryptography"}, new String[]{"Technology", "Finance", "Innovation"}));
        list.add(c("Mobile App Developer", "Build iOS and Android applications", "Bachelor's in CS or related", "₹5-25 LPA", "Excellent", 72, null, 65, 68, new String[]{"Swift/Kotlin", "React Native", "UI/UX"}, new String[]{"Technology", "Mobile", "Design"}));
        list.add(c("Technical Writer", "Write documentation and user guides for software", "Bachelor's in English/Technical Writing", "₹3-15 LPA", "Good", 65, 80, null, 65, new String[]{"Writing", "Technical Communication", "Tools"}, new String[]{"Writing", "Technology", "Clarity"}));
        list.add(c("HR Manager", "Manage recruitment, policies, and employee relations", "Bachelor's/MBA in HR or Business", "₹5-22 LPA", "Good", null, 78, 62, null, new String[]{"Recruitment", "Communication", "Labour Laws"}, new String[]{"People", "Business", "Communication"}));
        list.add(c("Business Analyst", "Bridge business needs and IT solutions", "Bachelor's + BA certifications", "₹6-25 LPA", "Excellent", 75, 75, 72, null, new String[]{"Requirements", "Process Modeling", "SQL"}, new String[]{"Business", "Technology", "Analysis"}));
        list.add(c("Management Consultant", "Advise organizations on strategy and operations", "MBA or equivalent", "₹12-40 LPA", "Excellent", 78, 80, 75, null, new String[]{"Strategy", "Presentation", "Analysis"}, new String[]{"Business", "Strategy", "Problem Solving"}));
        list.add(c("Investment Banker", "Raise capital and advise on M&A and deals", "Bachelor's in Finance + MBA preferred", "₹15-50 LPA", "Excellent", 75, 72, 85, null, new String[]{"Financial Modeling", "Valuation", "Networking"}, new String[]{"Finance", "Business", "Deals"}));
        list.add(c("Actuary", "Assess risk and design insurance/pension products", "Bachelor's in Math/Stats + actuarial exams", "₹8-30 LPA", "Excellent", 80, 68, 88, null, new String[]{"Statistics", "Risk", "Excel"}, new String[]{"Mathematics", "Finance", "Risk"}));
        list.add(c("Economist", "Analyze economic data and policy", "Master's/PhD in Economics", "₹6-28 LPA", "Good", 78, 75, 82, null, new String[]{"Econometrics", "Research", "Writing"}, new String[]{"Economics", "Policy", "Research"}));
        list.add(c("Pharmacist", "Dispense medicines and advise on drug use", "B.Pharm / M.Pharm", "₹3-15 LPA", "Good", 70, 68, 72, null, new String[]{"Pharmacy", "Regulations", "Patient Care"}, new String[]{"Healthcare", "Science", "Helping Others"}));
        list.add(c("Dentist", "Diagnose and treat oral health issues", "BDS + MDS for specialization", "₹5-25 LPA", "Good", 72, 70, 68, null, new String[]{"Dental Procedures", "Patient Care", "Manual Dexterity"}, new String[]{"Healthcare", "Biology", "Helping Others"}));
        list.add(c("Physiotherapist", "Restore movement and manage pain through therapy", "Bachelor's in Physiotherapy", "₹3-15 LPA", "Good", 65, 70, 62, null, new String[]{"Anatomy", "Exercise Therapy", "Communication"}, new String[]{"Healthcare", "Sports", "Helping Others"}));
        list.add(c("Veterinarian", "Treat and care for animals", "BVSc or equivalent", "₹4-18 LPA", "Good", 72, 65, 68, null, new String[]{"Animal Medicine", "Surgery", "Compassion"}, new String[]{"Animals", "Biology", "Healthcare"}));
        list.add(c("Biomedical Engineer", "Design medical devices and equipment", "Bachelor's in Biomedical Engineering", "₹5-22 LPA", "Excellent", 78, null, 75, 70, new String[]{"Medical Devices", "Signal Processing", "Regulations"}, new String[]{"Healthcare", "Technology", "Innovation"}));
        list.add(c("Journalist", "Research and report news across media", "Bachelor's in Journalism/Mass Communication", "₹2-15 LPA", "Moderate", null, 82, 58, 72, new String[]{"Writing", "Research", "Interviewing"}, new String[]{"Writing", "Current Affairs", "Storytelling"}));
        list.add(c("Social Media Manager", "Manage brand presence on social platforms", "Bachelor's + marketing/social media experience", "₹3-15 LPA", "Excellent", null, 72, 60, 78, new String[]{"Content", "Analytics", "Creativity"}, new String[]{"Marketing", "Social Media", "Creativity"}));
        list.add(c("Video Editor", "Edit video content for films, ads, and digital", "Bachelor's/Diploma in Film/Media", "₹2-12 LPA", "Good", null, 62, 55, 82, new String[]{"Editing Software", "Storytelling", "Color Grading"}, new String[]{"Film", "Creativity", "Media"}));
        list.add(c("Animator", "Create 2D/3D animation for film, games, and ads", "Bachelor's in Animation/Design", "₹3-18 LPA", "Good", 65, 60, 58, 85, new String[]{"3D Software", "Drawing", "Storyboarding"}, new String[]{"Animation", "Art", "Creativity"}));
        list.add(c("Fashion Designer", "Design clothing and accessories", "Bachelor's in Fashion Design", "₹3-20 LPA", "Good", null, 62, 55, 88, new String[]{"Sketching", "Textiles", "Trends"}, new String[]{"Fashion", "Art", "Creativity"}));
        list.add(c("Interior Designer", "Design functional and aesthetic interior spaces", "Bachelor's in Interior Design", "₹3-18 LPA", "Good", 62, 65, 58, 82, new String[]{"Space Planning", "CAD", "Materials"}, new String[]{"Design", "Art", "Buildings"}));
        list.add(c("Photographer", "Capture images for commercial or artistic use", "Diploma/Degree in Photography", "₹2-15 LPA", "Moderate", null, 60, 55, 85, new String[]{"Composition", "Lighting", "Editing"}, new String[]{"Art", "Creativity", "Visual Storytelling"}));
        list.add(c("Copywriter", "Write persuasive ad and marketing copy", "Bachelor's in English/Marketing", "₹3-15 LPA", "Good", null, 82, 58, 78, new String[]{"Copywriting", "Brand Voice", "Research"}, new String[]{"Writing", "Marketing", "Creativity"}));
        list.add(c("Public Relations Specialist", "Manage public image and media relations", "Bachelor's in PR/Communications", "₹3-18 LPA", "Good", null, 82, 60, 70, new String[]{"Communication", "Crisis Management", "Networking"}, new String[]{"Communication", "Media", "Business"}));
        list.add(c("Event Manager", "Plan and execute events and conferences", "Bachelor's in Event Management/Hospitality", "₹3-15 LPA", "Good", 62, 75, 65, 78, new String[]{"Planning", "Vendor Management", "Logistics"}, new String[]{"Events", "Creativity", "People"}));
        list.add(c("Chef", "Prepare and present food in restaurants or hotels", "Diploma/Degree in Culinary Arts", "₹2-15 LPA", "Good", 58, 55, 55, 80, new String[]{"Culinary Skills", "Menu Planning", "Hygiene"}, new String[]{"Food", "Creativity", "Hospitality"}));
        list.add(c("Hotel Manager", "Oversee hotel operations and guest experience", "Bachelor's in Hospitality Management", "₹4-20 LPA", "Good", 68, 75, 68, null, new String[]{"Operations", "Customer Service", "Team Management"}, new String[]{"Hospitality", "Business", "People"}));
        list.add(c("Travel Blogger", "Create travel content and influence travel decisions", "No strict requirement; writing and media skills", "₹2-15 LPA", "Moderate", null, 78, 55, 82, new String[]{"Writing", "Photography", "Social Media"}, new String[]{"Travel", "Writing", "Creativity"}));
        list.add(c("Pilot", "Fly aircraft for airlines or cargo", "Commercial Pilot License + training", "₹8-40 LPA", "Good", 78, 70, 82, null, new String[]{"Aviation", "Decision Making", "Focus"}, new String[]{"Aviation", "Travel", "Technology"}));
        list.add(c("Air Hostess / Flight Attendant", "Ensure passenger safety and comfort on flights", "Diploma in Aviation/Hospitality", "₹3-12 LPA", "Moderate", 60, 78, 55, 65, new String[]{"Customer Service", "Safety", "Communication"}, new String[]{"Aviation", "Travel", "People"}));
        list.add(c("Marine Engineer", "Design and maintain ships and marine systems", "Bachelor's in Marine Engineering", "₹6-25 LPA", "Good", 75, null, 78, null, new String[]{"Marine Systems", "Mechanical", "Safety"}, new String[]{"Marine", "Engineering", "Technology"}));
        list.add(c("Environmental Engineer", "Address environmental issues through engineering", "Bachelor's in Environmental/Civil/Chemical Eng", "₹4-18 LPA", "Good", 72, 65, 72, 68, new String[]{"Environmental Science", "Regulations", "Design"}, new String[]{"Environment", "Science", "Problem Solving"}));
        list.add(c("Electrical Engineer", "Design and maintain electrical systems", "Bachelor's in Electrical Engineering", "₹4-22 LPA", "Good", 75, null, 75, null, new String[]{"Circuit Design", "Power Systems", "Standards"}, new String[]{"Technology", "Innovation", "Problem Solving"}));
        list.add(c("Electronics Engineer", "Design electronic circuits and devices", "Bachelor's in Electronics/ECE", "₹4-20 LPA", "Good", 78, null, 75, 65, new String[]{"Circuit Design", "Embedded Systems", "Testing"}, new String[]{"Technology", "Innovation", "Electronics"}));
        list.add(c("Chemical Engineer", "Design processes for chemicals and materials", "Bachelor's in Chemical Engineering", "₹5-22 LPA", "Good", 75, null, 80, 65, new String[]{"Process Design", "Chemistry", "Safety"}, new String[]{"Chemistry", "Industry", "Problem Solving"}));
        list.add(c("Aerospace Engineer", "Design aircraft and spacecraft", "Bachelor's in Aerospace Engineering", "₹6-28 LPA", "Good", 80, null, 78, 70, new String[]{"Aerodynamics", "Structures", "Simulation"}, new String[]{"Aviation", "Technology", "Innovation"}));
        list.add(c("Robotics Engineer", "Design and build robots and automation systems", "Bachelor's in Robotics/Mechanical/ECE", "₹6-25 LPA", "Excellent", 82, null, 78, 72, new String[]{"Control Systems", "Programming", "Mechanics"}, new String[]{"Technology", "Robotics", "Innovation"}));
        list.add(c("Geologist", "Study Earth's structure and resources", "Bachelor's/Master's in Geology", "₹4-20 LPA", "Moderate", 75, 65, 72, null, new String[]{"Field Work", "Analysis", "Report Writing"}, new String[]{"Science", "Nature", "Research"}));
        list.add(c("Astronomer", "Study celestial objects and phenomena", "PhD in Astronomy/Physics", "₹6-22 LPA", "Moderate", 85, 70, 85, null, new String[]{"Physics", "Data Analysis", "Research"}, new String[]{"Space", "Science", "Mathematics"}));
        list.add(c("Statistician", "Collect and analyze data for decision-making", "Master's in Statistics", "₹5-25 LPA", "Excellent", 80, 68, 88, null, new String[]{"Statistical Methods", "R/Python", "Research"}, new String[]{"Mathematics", "Data", "Research"}));
        list.add(c("Mathematician", "Develop and apply mathematical theories", "Master's/PhD in Mathematics", "₹5-22 LPA", "Moderate", 88, 70, 90, null, new String[]{"Proof", "Modeling", "Computation"}, new String[]{"Mathematics", "Research", "Problem Solving"}));
        list.add(c("Physicist", "Research matter, energy, and fundamental laws", "PhD in Physics", "₹6-25 LPA", "Moderate", 85, 72, 88, 65, new String[]{"Theoretical/Experimental Physics", "Math", "Research"}, new String[]{"Science", "Mathematics", "Discovery"}));
        list.add(c("Chemist", "Study substances and chemical reactions", "Master's/PhD in Chemistry", "₹4-18 LPA", "Moderate", 78, 68, 80, 62, new String[]{"Lab Work", "Analysis", "Safety"}, new String[]{"Science", "Research", "Chemistry"}));
        list.add(c("Biologist", "Study living organisms and ecosystems", "Master's/PhD in Biology/Life Sciences", "₹4-18 LPA", "Moderate", 75, 70, 72, 65, new String[]{"Research", "Lab Work", "Analysis"}, new String[]{"Biology", "Nature", "Research"}));
        list.add(c("Biotechnologist", "Apply biology to develop products and processes", "Bachelor's/Master's in Biotechnology", "₹4-22 LPA", "Excellent", 78, 65, 75, 68, new String[]{"Lab Techniques", "Molecular Biology", "Regulations"}, new String[]{"Biology", "Technology", "Research"}));
        list.add(c("Pharmacologist", "Study drug effects and develop new medicines", "PhD in Pharmacology", "₹6-25 LPA", "Good", 80, 70, 78, null, new String[]{"Research", "Clinical Trials", "Analysis"}, new String[]{"Healthcare", "Research", "Science"}));
        list.add(c("Nutritionist", "Advise on diet and nutrition for health", "Bachelor's in Nutrition/Dietetics", "₹3-12 LPA", "Good", 65, 72, 65, null, new String[]{"Nutrition Science", "Counseling", "Meal Planning"}, new String[]{"Health", "Food", "Helping Others"}));
        list.add(c("Yoga Instructor", "Teach yoga and wellness practices", "Certification in Yoga", "₹2-10 LPA", "Moderate", 55, 72, 50, 70, new String[]{"Yoga", "Anatomy", "Communication"}, new String[]{"Fitness", "Wellness", "Teaching"}));
        list.add(c("Sports Coach", "Train athletes and improve performance", "Degree/Diploma in Sports Science", "₹3-15 LPA", "Moderate", 65, 72, 60, 65, new String[]{"Training", "Motivation", "Strategy"}, new String[]{"Sports", "Fitness", "Teaching"}));
        list.add(c("Police Officer", "Enforce law and maintain public order", "Graduation + physical eligibility", "₹4-12 LPA", "Moderate", 68, 70, 62, 58, new String[]{"Law", "Physical Fitness", "Decision Making"}, new String[]{"Law", "Public Service", "Justice"}));
        list.add(c("Defense Officer", "Serve in armed forces and protect the nation", "NDA/CDS or equivalent", "₹6-18 LPA", "Moderate", 72, 70, 72, 62, new String[]{"Leadership", "Discipline", "Strategy"}, new String[]{"Defense", "Patriotism", "Leadership"}));
        list.add(c("Firefighter", "Respond to fires and emergencies", "High school + firefighting training", "₹3-10 LPA", "Moderate", 65, 62, 60, 58, new String[]{"Physical Fitness", "Crisis Response", "Teamwork"}, new String[]{"Public Service", "Rescue", "Courage"}));
        list.add(c("Social Worker", "Support vulnerable individuals and communities", "Bachelor's/Master's in Social Work", "₹2-10 LPA", "Moderate", 60, 78, 55, 70, new String[]{"Counseling", "Community Work", "Empathy"}, new String[]{"Helping Others", "Society", "Empathy"}));
        list.add(c("Counselor", "Provide guidance on career, education, and personal issues", "Master's in Psychology/Counseling", "₹3-15 LPA", "Good", 65, 82, 58, 68, new String[]{"Listening", "Empathy", "Assessment"}, new String[]{"Helping Others", "Psychology", "Communication"}));
        list.add(c("Librarian", "Manage library resources and help users find information", "Bachelor's/Master's in Library Science", "₹3-10 LPA", "Moderate", 65, 72, 62, 60, new String[]{"Cataloging", "Research", "Customer Service"}, new String[]{"Books", "Research", "Knowledge"}));
        list.add(c("Translator", "Translate written content between languages", "Bachelor's + fluency in languages", "₹3-15 LPA", "Good", 65, 88, 58, 65, new String[]{"Languages", "Writing", "Cultural Knowledge"}, new String[]{"Languages", "Writing", "Culture"}));
        list.add(c("Interpreter", "Provide real-time spoken translation", "Fluency in languages + certification", "₹4-18 LPA", "Good", 68, 88, 60, 62, new String[]{"Languages", "Quick Thinking", "Listening"}, new String[]{"Languages", "Communication", "Culture"}));
        list.add(c("Archaeologist", "Study human history through material remains", "Master's in Archaeology", "₹4-15 LPA", "Moderate", 75, 72, 68, 70, new String[]{"Excavation", "Analysis", "Writing"}, new String[]{"History", "Discovery", "Research"}));
        list.add(c("Historian", "Research and interpret past events", "Master's/PhD in History", "₹4-15 LPA", "Moderate", 72, 82, 65, 68, new String[]{"Research", "Writing", "Analysis"}, new String[]{"History", "Research", "Writing"}));
        list.add(c("Museum Curator", "Manage collections and exhibitions", "Master's in History/Art/Museum Studies", "₹4-15 LPA", "Moderate", 68, 75, 62, 78, new String[]{"Curating", "Research", "Presentation"}, new String[]{"Art", "History", "Culture"}));
        list.add(c("Real Estate Agent", "Buy, sell, and rent properties for clients", "High school + real estate license", "₹3-20 LPA", "Good", 65, 78, 68, 62, new String[]{"Sales", "Negotiation", "Market Knowledge"}, new String[]{"Sales", "Property", "Business"}));
        list.add(c("Insurance Agent", "Sell insurance policies and advise clients", "High school + insurance license", "₹3-12 LPA", "Moderate", 65, 75, 70, 58, new String[]{"Sales", "Product Knowledge", "Communication"}, new String[]{"Finance", "Sales", "People"}));
        list.add(c("Sales Manager", "Lead sales teams and meet targets", "Bachelor's + sales experience", "₹6-25 LPA", "Good", 70, 80, 72, 65, new String[]{"Sales", "Leadership", "Strategy"}, new String[]{"Sales", "Business", "People"}));
        list.add(c("Supply Chain Manager", "Manage logistics and supply chain operations", "Bachelor's/MBA in Supply Chain/Operations", "₹6-25 LPA", "Good", 75, 68, 78, 62, new String[]{"Logistics", "Planning", "Vendor Management"}, new String[]{"Operations", "Business", "Logistics"}));
        list.add(c("Quality Assurance Engineer", "Test software and ensure quality", "Bachelor's in CS or related", "₹4-18 LPA", "Good", 75, 65, 68, 65, new String[]{"Testing", "Automation", "Bug Tracking"}, new String[]{"Technology", "Quality", "Problem Solving"}));
        list.add(c("Scrum Master", "Facilitate agile teams and remove blockers", "Bachelor's + Agile/Scrum certification", "₹8-25 LPA", "Excellent", 72, 78, 65, 68, new String[]{"Agile", "Facilitation", "Coaching"}, new String[]{"Technology", "Teamwork", "Process"}));
        list.add(c("Data Engineer", "Build and maintain data pipelines and warehouses", "Bachelor's in CS/IT", "₹8-28 LPA", "Excellent", 78, null, 75, null, new String[]{"SQL", "ETL", "Big Data"}, new String[]{"Data", "Technology", "Infrastructure"}));
        list.add(c("Cloud Architect", "Design and manage cloud infrastructure", "Bachelor's in CS + cloud certifications", "₹12-35 LPA", "Excellent", 80, 65, 72, null, new String[]{"AWS/Azure/GCP", "Architecture", "Security"}, new String[]{"Technology", "Cloud", "Infrastructure"}));
        list.add(c("Full Stack Developer", "Build front-end and back-end of web applications", "Bachelor's in CS or related", "₹6-28 LPA", "Excellent", 75, null, 68, 68, new String[]{"JavaScript", "APIs", "Databases"}, new String[]{"Technology", "Coding", "Web"}));
        list.add(c("Frontend Developer", "Build user interfaces for web and mobile", "Bachelor's in CS or related", "₹5-22 LPA", "Excellent", 72, null, 62, 75, new String[]{"HTML/CSS/JS", "React/Vue", "UI"}, new String[]{"Technology", "Design", "Coding"}));
        list.add(c("Backend Developer", "Build server-side logic and APIs", "Bachelor's in CS or related", "₹6-25 LPA", "Excellent", 78, null, 70, 62, new String[]{"Java/Python/Node", "Databases", "APIs"}, new String[]{"Technology", "Coding", "Logic"}));
        list.add(c("Database Administrator", "Manage and optimize databases", "Bachelor's in CS/IT", "₹5-22 LPA", "Good", 78, 62, 75, null, new String[]{"SQL", "Performance", "Security"}, new String[]{"Technology", "Data", "Infrastructure"}));
        list.add(c("Network Engineer", "Design and maintain computer networks", "Bachelor's in CS/IT/ECE", "₹5-20 LPA", "Good", 75, 62, 72, null, new String[]{"Networking", "Security", "Troubleshooting"}, new String[]{"Technology", "Networks", "Infrastructure"}));
        list.add(c("SEO Specialist", "Improve website visibility on search engines", "Bachelor's + SEO certifications", "₹3-15 LPA", "Good", 70, 72, 68, 70, new String[]{"SEO", "Analytics", "Content"}, new String[]{"Marketing", "Technology", "Analytics"}));
        list.add(c("Growth Hacker", "Drive user and revenue growth through experiments", "Bachelor's + marketing/tech background", "₹6-25 LPA", "Excellent", 75, 72, 75, 78, new String[]{"Experimentation", "Analytics", "Marketing"}, new String[]{"Marketing", "Technology", "Growth"}));
        list.add(c("Brand Manager", "Build and manage brand identity and campaigns", "Bachelor's/MBA in Marketing", "₹6-25 LPA", "Good", 68, 78, 65, 80, new String[]{"Brand Strategy", "Campaigns", "Research"}, new String[]{"Marketing", "Creativity", "Business"}));
        list.add(c("Market Research Analyst", "Gather and analyze market data", "Bachelor's in Marketing/Statistics", "₹4-18 LPA", "Good", 75, 72, 78, 65, new String[]{"Research", "Surveys", "Analysis"}, new String[]{"Research", "Business", "Data"}));
        list.add(c("Entrepreneur", "Start and run your own business", "No fixed path; business acumen", "Varies", "Varies", 75, 78, 70, 82, new String[]{"Risk-taking", "Leadership", "Strategy"}, new String[]{"Business", "Innovation", "Leadership"}));
        list.add(c("Startup Founder", "Build and scale a new company", "No fixed path; idea and execution", "Varies", "Varies", 78, 75, 72, 85, new String[]{"Vision", "Fundraising", "Team Building"}, new String[]{"Innovation", "Technology", "Business"}));
        list.add(c("Venture Capital Analyst", "Evaluate and invest in startups", "Bachelor's/MBA + finance/tech", "₹8-30 LPA", "Excellent", 78, 75, 80, 70, new String[]{"Due Diligence", "Financial Modeling", "Networking"}, new String[]{"Finance", "Startups", "Business"}));
        list.add(c("Tax Consultant", "Advise on tax planning and compliance", "CA/CS or tax specialization", "₹5-22 LPA", "Good", 72, 70, 82, null, new String[]{"Tax Law", "Compliance", "Planning"}, new String[]{"Finance", "Law", "Business"}));
        list.add(c("Auditor", "Examine financial records and compliance", "CA or equivalent", "₹5-20 LPA", "Good", 75, 68, 82, null, new String[]{"Auditing", "Compliance", "Analysis"}, new String[]{"Finance", "Compliance", "Analysis"}));
        list.add(c("Cost Accountant", "Analyze costs and support budgeting", "ICWA/CMA or equivalent", "₹4-18 LPA", "Good", 72, 65, 80, null, new String[]{"Costing", "Budgeting", "Excel"}, new String[]{"Finance", "Numbers", "Business"}));
        list.add(c("Company Secretary", "Ensure corporate compliance and governance", "CS qualification", "₹5-22 LPA", "Good", 72, 78, 70, null, new String[]{"Company Law", "Compliance", "Secretarial"}, new String[]{"Law", "Business", "Compliance"}));
        list.add(c("Judge", "Preside over court and deliver judgments", "LLB + experience as lawyer", "₹15-40 LPA", "Moderate", 85, 88, 75, 68, new String[]{"Law", "Judgment", "Ethics"}, new String[]{"Law", "Justice", "Society"}));
        list.add(c("Legal Advisor", "Provide in-house legal counsel to organizations", "LLB + experience", "₹6-25 LPA", "Good", 75, 80, 68, null, new String[]{"Corporate Law", "Contracts", "Compliance"}, new String[]{"Law", "Business", "Advisory"}));
        list.add(c("Patent Attorney", "Advise on intellectual property and patents", "LLB + technical background", "₹8-35 LPA", "Excellent", 80, 82, 75, 68, new String[]{"IP Law", "Patents", "Technical Writing"}, new String[]{"Law", "Technology", "Innovation"}));
        list.add(c("Professor", "Teach and research at universities", "PhD in relevant subject", "₹8-25 LPA", "Moderate", 80, 82, 75, 70, new String[]{"Teaching", "Research", "Publication"}, new String[]{"Education", "Research", "Knowledge"}));
        list.add(c("School Principal", "Lead school operations and academics", "B.Ed + Master's + experience", "₹6-18 LPA", "Moderate", 75, 82, 68, 70, new String[]{"Leadership", "Administration", "Education"}, new String[]{"Education", "Leadership", "Management"}));
        list.add(c("Trainer", "Deliver corporate or skill-based training", "Bachelor's + domain expertise", "₹4-18 LPA", "Good", 68, 82, 62, 72, new String[]{"Training", "Presentation", "Content Design"}, new String[]{"Teaching", "Communication", "Skills"}));
        list.add(c("Instructional Designer", "Design learning content and courses", "Bachelor's in Education/Instructional Design", "₹4-18 LPA", "Good", 72, 78, 65, 80, new String[]{"Curriculum Design", "E-learning", "Pedagogy"}, new String[]{"Education", "Design", "Technology"}));
        list.add(c("EdTech Specialist", "Build or manage educational technology products", "Bachelor's in CS/Education", "₹5-22 LPA", "Excellent", 75, 72, 70, 75, new String[]{"EdTech", "Product", "Learning Design"}, new String[]{"Education", "Technology", "Innovation"}));
        list.add(c("Speech Therapist", "Help people with speech and language disorders", "Bachelor's/Master's in Speech-Language Pathology", "₹3-15 LPA", "Good", 68, 82, 62, 68, new String[]{"Assessment", "Therapy", "Patience"}, new String[]{"Healthcare", "Helping Others", "Communication"}));
        list.add(c("Occupational Therapist", "Help people regain daily living skills", "Bachelor's in Occupational Therapy", "₹3-14 LPA", "Good", 70, 75, 65, 72, new String[]{"Rehabilitation", "Assessment", "Empathy"}, new String[]{"Healthcare", "Helping Others", "Rehabilitation"}));
        list.add(c("Radiologist", "Interpret medical images (X-ray, MRI, CT)", "MBBS + MD in Radiology", "₹12-40 LPA", "Excellent", 82, 68, 80, null, new String[]{"Imaging", "Diagnosis", "Technology"}, new String[]{"Healthcare", "Technology", "Diagnosis"}));
        list.add(c("Surgeon", "Perform surgical procedures", "MBBS + MS in Surgery", "₹15-50 LPA", "Excellent", 85, 72, 78, 72, new String[]{"Surgery", "Precision", "Decision Making"}, new String[]{"Healthcare", "Surgery", "Precision"}));
        list.add(c("Pediatrician", "Care for infants, children, and adolescents", "MBBS + MD in Pediatrics", "₹8-30 LPA", "Good", 78, 80, 72, 70, new String[]{"Child Health", "Communication", "Empathy"}, new String[]{"Healthcare", "Children", "Helping Others"}));
        list.add(c("Cardiologist", "Specialize in heart and cardiovascular system", "MBBS + DM in Cardiology", "₹18-60 LPA", "Excellent", 85, 72, 80, null, new String[]{"Cardiology", "Diagnosis", "Procedures"}, new String[]{"Healthcare", "Medicine", "Specialization"}));
        list.add(c("Dermatologist", "Treat skin, hair, and nail conditions", "MBBS + MD in Dermatology", "₹8-35 LPA", "Good", 75, 72, 70, 68, new String[]{"Dermatology", "Procedures", "Patient Care"}, new String[]{"Healthcare", "Skin", "Aesthetics"}));
        list.add(c("Orthopedic Surgeon", "Treat bone, joint, and muscle conditions", "MBBS + MS in Orthopedics", "₹12-45 LPA", "Good", 82, 70, 75, 68, new String[]{"Surgery", "Bones", "Rehabilitation"}, new String[]{"Healthcare", "Surgery", "Sports"}));
        list.add(c("Pilot (Commercial)", "Fly passengers and cargo for airlines", "Commercial Pilot License + type rating", "₹10-45 LPA", "Good", 80, 72, 82, 65, new String[]{"Aviation", "Decision Making", "Focus"}, new String[]{"Aviation", "Travel", "Technology"}));
        list.add(c("Ship Captain", "Command merchant or passenger vessels", "Maritime training + certifications", "₹8-30 LPA", "Moderate", 78, 75, 75, 62, new String[]{"Navigation", "Leadership", "Safety"}, new String[]{"Marine", "Travel", "Leadership"}));
        list.add(c("Logistics Manager", "Plan and manage movement of goods", "Bachelor's in Logistics/Operations", "₹5-22 LPA", "Good", 72, 68, 75, 62, new String[]{"Logistics", "Planning", "Vendor Management"}, new String[]{"Operations", "Business", "Logistics"}));
        list.add(c("Procurement Manager", "Source and purchase materials and services", "Bachelor's + procurement experience", "₹5-20 LPA", "Good", 72, 75, 75, 60, new String[]{"Negotiation", "Vendor Management", "Contracts"}, new String[]{"Business", "Procurement", "Negotiation"}));
        list.add(c("Project Manager", "Plan and execute projects within scope and time", "Bachelor's + PMP/PRINCE2", "₹8-28 LPA", "Excellent", 75, 78, 72, 68, new String[]{"Planning", "Stakeholder Management", "Risk"}, new String[]{"Management", "Organization", "Leadership"}));
        list.add(c("Operations Manager", "Oversee day-to-day operations", "Bachelor's/MBA in Operations", "₹6-25 LPA", "Good", 75, 72, 75, 62, new String[]{"Operations", "Efficiency", "Team Management"}, new String[]{"Business", "Operations", "Management"}));
        list.add(c("Human Resources Director", "Lead HR strategy and teams", "Bachelor's/MBA in HR", "₹12-35 LPA", "Good", 72, 82, 68, 68, new String[]{"HR Strategy", "Talent", "Compliance"}, new String[]{"People", "Business", "Leadership"}));
        list.add(c("Recruiter", "Source and hire talent for organizations", "Bachelor's + recruitment experience", "₹3-15 LPA", "Good", 65, 80, 60, 65, new String[]{"Sourcing", "Interviewing", "Communication"}, new String[]{"People", "HR", "Communication"}));
        list.add(c("Compliance Officer", "Ensure regulatory and policy compliance", "Bachelor's + legal/finance background", "₹5-22 LPA", "Good", 75, 78, 72, 62, new String[]{"Compliance", "Regulations", "Risk"}, new String[]{"Compliance", "Law", "Business"}));
        list.add(c("Risk Manager", "Identify and mitigate business risks", "Bachelor's/MBA + risk certifications", "₹6-25 LPA", "Good", 78, 72, 80, 65, new String[]{"Risk Assessment", "Analysis", "Policies"}, new String[]{"Risk", "Finance", "Business"}));
        list.add(c("Sustainability Consultant", "Advise on environmental and sustainability strategy", "Bachelor's in Environment/Sustainability", "₹5-22 LPA", "Excellent", 72, 75, 70, 72, new String[]{"Sustainability", "Reporting", "Strategy"}, new String[]{"Environment", "Business", "Strategy"}));
        list.add(c("Freelance Writer", "Write content for clients on a project basis", "No fixed requirement; writing skills", "₹2-15 LPA", "Moderate", null, 82, 55, 78, new String[]{"Writing", "Research", "Deadlines"}, new String[]{"Writing", "Creativity", "Independence"}));
        list.add(c("Podcast Host", "Create and host audio content", "No fixed requirement; communication skills", "₹2-20 LPA", "Good", null, 85, 55, 82, new String[]{"Speaking", "Research", "Editing"}, new String[]{"Media", "Communication", "Creativity"}));
        list.add(c("Influencer", "Build audience and partner with brands", "No fixed requirement; content and reach", "₹3-50 LPA", "Moderate", 60, 75, 55, 85, new String[]{"Content", "Social Media", "Personal Brand"}, new String[]{"Social Media", "Creativity", "Marketing"}));
        list.add(c("Voiceover Artist", "Narrate for ads, films, and audiobooks", "Training in voice and diction", "₹2-15 LPA", "Moderate", null, 85, 50, 75, new String[]{"Voice", "Diction", "Acting"}, new String[]{"Media", "Creativity", "Performance"}));
        list.add(c("Stand-up Comedian", "Perform comedy and entertain audiences", "No fixed requirement; talent and practice", "₹2-30 LPA", "Moderate", 72, 88, 55, 90, new String[]{"Writing", "Performance", "Timing"}, new String[]{"Comedy", "Performance", "Creativity"}));
        list.add(c("Music Producer", "Produce and mix music tracks", "Diploma/Degree in Music Production", "₹3-25 LPA", "Moderate", 68, 60, 65, 88, new String[]{"DAW", "Mixing", "Sound Design"}, new String[]{"Music", "Technology", "Creativity"}));
        list.add(c("Sound Engineer", "Record and edit audio for film, TV, and music", "Diploma in Sound Engineering", "₹2-15 LPA", "Moderate", 70, 62, 68, 78, new String[]{"Recording", "Mixing", "Equipment"}, new String[]{"Audio", "Technology", "Music"}));
        list.add(c("Film Director", "Direct films and creative vision", "Film school or experience", "₹5-50 LPA", "Moderate", 75, 78, 62, 92, new String[]{"Storytelling", "Leadership", "Visuals"}, new String[]{"Film", "Creativity", "Leadership"}));
        list.add(c("Screenwriter", "Write scripts for films and TV", "Bachelor's in Film/Writing or experience", "₹3-25 LPA", "Moderate", 72, 85, 60, 88, new String[]{"Storytelling", "Dialogue", "Structure"}, new String[]{"Writing", "Film", "Creativity"}));
        list.add(c("Theatre Actor", "Perform in stage productions", "Training in theatre/acting", "₹2-15 LPA", "Moderate", 65, 85, 55, 88, new String[]{"Acting", "Voice", "Movement"}, new String[]{"Theatre", "Performance", "Art"}));
        list.add(c("Dancer", "Perform and choreograph dance", "Training in dance forms", "₹2-18 LPA", "Moderate", 58, 65, 50, 90, new String[]{"Dance", "Fitness", "Expression"}, new String[]{"Dance", "Performance", "Art"}));
        list.add(c("Singer", "Perform and record vocal music", "Training in music and voice", "₹2-30 LPA", "Moderate", 60, 82, 52, 88, new String[]{"Vocal", "Music", "Performance"}, new String[]{"Music", "Performance", "Creativity"}));
        list.add(c("Ethical Hacker", "Test systems for security vulnerabilities", "Bachelor's in CS/IT + CEH/OSCP", "₹6-28 LPA", "Excellent", 80, 65, 72, 68, new String[]{"Penetration Testing", "Networks", "Scripting"}, new String[]{"Security", "Technology", "Problem Solving"}));
        list.add(c("AR/VR Developer", "Build augmented and virtual reality experiences", "Bachelor's in CS/Design", "₹6-25 LPA", "Excellent", 75, 62, 68, 82, new String[]{"Unity", "3D", "Interaction Design"}, new String[]{"Technology", "Gaming", "Creativity"}));
        list.add(c("IoT Engineer", "Design connected devices and sensor systems", "Bachelor's in ECE/CS/IT", "₹5-22 LPA", "Excellent", 78, 62, 72, 68, new String[]{"Embedded Systems", "Sensors", "Protocols"}, new String[]{"Technology", "Hardware", "Innovation"}));
        // More technical careers
        list.add(c("Site Reliability Engineer", "Keep systems running and improve reliability at scale", "Bachelor's in CS/IT + DevOps/SRE experience", "₹10-32 LPA", "Excellent", 78, 65, 72, 65, new String[]{"Linux", "Monitoring", "Automation", "Incident Management"}, new String[]{"Technology", "Infrastructure", "Automation"}));
        list.add(c("Solutions Architect", "Design end-to-end technical solutions for business problems", "Bachelor's in CS + experience", "₹15-40 LPA", "Excellent", 82, 75, 75, 68, new String[]{"Architecture", "Cloud", "Integration", "Security"}, new String[]{"Technology", "Business", "Innovation"}));
        list.add(c("Software Architect", "Define high-level design and tech stack for applications", "Bachelor's in CS + senior experience", "₹18-45 LPA", "Excellent", 85, 72, 78, 70, new String[]{"System Design", "Patterns", "Scalability", "APIs"}, new String[]{"Technology", "Coding", "Problem Solving"}));
        list.add(c("Platform Engineer", "Build and maintain internal platforms and tooling for developers", "Bachelor's in CS/IT", "₹10-30 LPA", "Excellent", 78, 65, 72, 68, new String[]{"Kubernetes", "CI/CD", "APIs", "Automation"}, new String[]{"Technology", "Infrastructure", "Automation"}));
        list.add(c("MLOps Engineer", "Deploy and maintain machine learning models in production", "Bachelor's in CS/Math/Stats", "₹12-35 LPA", "Excellent", 80, 65, 78, 65, new String[]{"ML", "Docker", "Kubernetes", "Monitoring"}, new String[]{"Technology", "Data", "Automation"}));
        list.add(c("Computer Vision Engineer", "Build systems that understand images and video", "Bachelor's/Master's in CS/ECE", "₹10-32 LPA", "Excellent", 82, 62, 78, 70, new String[]{"Python", "Deep Learning", "OpenCV", "TensorFlow"}, new String[]{"Technology", "Mathematics", "Innovation"}));
        list.add(c("NLP Engineer", "Work on language models, chatbots, and text analytics", "Bachelor's/Master's in CS/Linguistics", "₹10-32 LPA", "Excellent", 80, 72, 75, 68, new String[]{"NLP", "Python", "Transformers", "APIs"}, new String[]{"Technology", "Data", "Innovation"}));
        list.add(c("Embedded Systems Engineer", "Develop software for hardware devices and real-time systems", "Bachelor's in ECE/CS", "₹5-25 LPA", "Good", 80, 62, 75, 68, new String[]{"C/C++", "RTOS", "Microcontrollers", "Debugging"}, new String[]{"Technology", "Hardware", "Problem Solving"}));
        list.add(c("Firmware Engineer", "Write low-level software for electronic devices", "Bachelor's in ECE/CS", "₹5-22 LPA", "Good", 78, 60, 72, 65, new String[]{"C/C++", "Embedded", "Drivers", "Hardware"}, new String[]{"Technology", "Hardware", "Innovation"}));
        list.add(c("API Developer", "Design and build REST/GraphQL APIs and integrations", "Bachelor's in CS or related", "₹6-25 LPA", "Excellent", 75, 65, 68, 65, new String[]{"APIs", "REST", "Authentication", "Databases"}, new String[]{"Technology", "Coding", "Problem Solving"}));
        list.add(c("Automation Engineer", "Build test and deployment automation and scripts", "Bachelor's in CS/IT", "₹6-24 LPA", "Excellent", 76, 62, 70, 68, new String[]{"Scripting", "CI/CD", "Testing", "Python"}, new String[]{"Technology", "Automation", "Problem Solving"}));
        list.add(c("Business Intelligence Developer", "Build dashboards, reports, and data pipelines for BI", "Bachelor's in CS/Stats/Business", "₹6-26 LPA", "Excellent", 75, 68, 78, 65, new String[]{"SQL", "Power BI", "Tableau", "ETL"}, new String[]{"Data", "Technology", "Business"}));
        list.add(c("Analytics Engineer", "Transform raw data into analytics-ready datasets", "Bachelor's in CS/Stats", "₹8-28 LPA", "Excellent", 78, 65, 80, 65, new String[]{"SQL", "dbt", "Data Modeling", "Warehouses"}, new String[]{"Data", "Technology", "Problem Solving"}));
        list.add(c("Cloud Developer", "Build and optimize applications on AWS, Azure, or GCP", "Bachelor's in CS + cloud certifications", "₹8-28 LPA", "Excellent", 76, 62, 70, 66, new String[]{"Cloud", "Serverless", "APIs", "Security"}, new String[]{"Technology", "Infrastructure", "Innovation"}));
        list.add(c("Java Developer", "Develop enterprise applications using Java and Spring", "Bachelor's in CS or related", "₹5-25 LPA", "Excellent", 75, 62, 70, 62, new String[]{"Java", "Spring", "Databases", "APIs"}, new String[]{"Technology", "Coding", "Problem Solving"}));
        list.add(c("Python Developer", "Build applications and scripts using Python", "Bachelor's in CS or related", "₹5-24 LPA", "Excellent", 74, 65, 68, 65, new String[]{"Python", "Frameworks", "APIs", "Databases"}, new String[]{"Technology", "Coding", "Data"}));
        list.add(c("React Developer", "Build user interfaces with React and modern front-end tools", "Bachelor's in CS or related", "₹5-22 LPA", "Excellent", 72, 62, 65, 75, new String[]{"React", "JavaScript", "State", "APIs"}, new String[]{"Technology", "Coding", "Design"}));
        list.add(c("Node.js Developer", "Build server-side and full-stack apps with Node.js", "Bachelor's in CS or related", "₹5-24 LPA", "Excellent", 74, 62, 68, 68, new String[]{"Node.js", "JavaScript", "APIs", "Databases"}, new String[]{"Technology", "Coding", "Problem Solving"}));
        list.add(c("Flutter Developer", "Build cross-platform mobile apps with Flutter", "Bachelor's in CS or related", "₹5-22 LPA", "Excellent", 73, 62, 65, 72, new String[]{"Flutter", "Dart", "Mobile", "UI"}, new String[]{"Technology", "Coding", "Mobile"}));
        list.add(c("Go Developer", "Develop scalable systems and services with Go", "Bachelor's in CS or related", "₹8-28 LPA", "Excellent", 78, 62, 72, 65, new String[]{"Go", "Concurrency", "APIs", "Microservices"}, new String[]{"Technology", "Coding", "Infrastructure"}));
        list.add(c("Technical Lead", "Lead a dev team and own delivery and code quality", "Bachelor's in CS + experience", "₹14-38 LPA", "Excellent", 80, 75, 72, 68, new String[]{"Leadership", "Code Review", "Architecture", "Mentoring"}, new String[]{"Technology", "Leadership", "Problem Solving"}));
        list.add(c("IT Consultant", "Advise clients on technology strategy and implementation", "Bachelor's in CS/IT + experience", "₹8-28 LPA", "Good", 76, 78, 72, 68, new String[]{"Strategy", "Implementation", "Stakeholder Management"}, new String[]{"Technology", "Business", "Innovation"}));
        list.add(c("Systems Analyst", "Analyze business needs and design IT solutions", "Bachelor's in CS/IT/Business", "₹5-20 LPA", "Good", 75, 75, 72, 65, new String[]{"Analysis", "Requirements", "Process", "Documentation"}, new String[]{"Technology", "Business", "Problem Solving"}));
        list.add(c("Release Engineer", "Manage builds, releases, and deployment pipelines", "Bachelor's in CS/IT", "₹6-22 LPA", "Good", 74, 65, 70, 65, new String[]{"CI/CD", "Scripting", "Version Control", "Environments"}, new String[]{"Technology", "Automation", "Infrastructure"}));
        list.add(c("Technical Project Manager", "Manage technical projects and agile teams", "Bachelor's + PM/tech background", "₹10-30 LPA", "Excellent", 74, 78, 70, 68, new String[]{"Agile", "Planning", "Stakeholders", "Risk"}, new String[]{"Technology", "Business", "Leadership"}));
        return list;
    }

    private void initializeQuestions() {
        if (questionRepository.count() == 0) {
            // Logical Questions
            Question logical1 = new Question();
            logical1.setQuestionText("If all roses are flowers and some flowers fade quickly, which statement must be true?");
            logical1.setCategory("logical");
            logical1.setOptions(Arrays.asList(
                    "All roses fade quickly",
                    "Some roses fade quickly",
                    "No roses fade quickly",
                    "Cannot be determined"
            ));

            Question logical2 = new Question();
            logical2.setQuestionText("What comes next in the sequence: 2, 4, 8, 16, ?");
            logical2.setCategory("logical");
            logical2.setOptions(Arrays.asList("24", "32", "20", "18"));

            Question logical3 = new Question();
            logical3.setQuestionText("If no cats are dogs and some dogs are pets, what can we conclude?");
            logical3.setCategory("logical");
            logical3.setOptions(Arrays.asList("All pets are dogs", "Some pets are not cats", "No cats are pets", "Cannot be determined"));

            Question logical4 = new Question();
            logical4.setQuestionText("Complete the pattern: A1, B2, C3, D4, ?");
            logical4.setCategory("logical");
            logical4.setOptions(Arrays.asList("E4", "E5", "D5", "F5"));

            Question logical5 = new Question();
            logical5.setQuestionText("All books have pages. This object has pages. Therefore this object is a book. This reasoning is:");
            logical5.setCategory("logical");
            logical5.setOptions(Arrays.asList("Valid", "Invalid", "Sometimes valid", "Cannot say"));

            Question logical6 = new Question();
            logical6.setQuestionText("If A is taller than B, and B is taller than C, who is shortest?");
            logical6.setCategory("logical");
            logical6.setOptions(Arrays.asList("A", "B", "C", "Cannot determine"));

            // Verbal Questions
            Question verbal1 = new Question();
            verbal1.setQuestionText("Choose the word most similar to 'ELOQUENT':");
            verbal1.setCategory("verbal");
            verbal1.setOptions(Arrays.asList("Fluent", "Quiet", "Rough", "Simple"));

            Question verbal2 = new Question();
            verbal2.setQuestionText("Which word does not belong in the group?");
            verbal2.setCategory("verbal");
            verbal2.setOptions(Arrays.asList("Novel", "Biography", "Dictionary", "Fiction"));

            Question verbal3 = new Question();
            verbal3.setQuestionText("Choose the antonym of 'BENEVOLENT':");
            verbal3.setCategory("verbal");
            verbal3.setOptions(Arrays.asList("Kind", "Malevolent", "Generous", "Friendly"));

            Question verbal4 = new Question();
            verbal4.setQuestionText("Which sentence is grammatically correct?");
            verbal4.setCategory("verbal");
            verbal4.setOptions(Arrays.asList("Neither of them are right", "Neither of them is right", "Neither of them were right", "Neither of them be right"));

            Question verbal5 = new Question();
            verbal5.setQuestionText("What is the meaning of 'PRAGMATIC'?");
            verbal5.setCategory("verbal");
            verbal5.setOptions(Arrays.asList("Idealistic", "Practical", "Theoretical", "Abstract"));

            Question verbal6 = new Question();
            verbal6.setQuestionText("Identify the correctly spelled word:");
            verbal6.setCategory("verbal");
            verbal6.setOptions(Arrays.asList("Occurence", "Occurrence", "Ocurrence", "Occurance"));

            // Numerical Questions
            Question numerical1 = new Question();
            numerical1.setQuestionText("If a shirt costs ₹800 after a 20% discount, what was the original price?");
            numerical1.setCategory("numerical");
            numerical1.setOptions(Arrays.asList("₹960", "₹1000", "₹640", "₹1200"));

            Question numerical2 = new Question();
            numerical2.setQuestionText("What is 15% of 240?");
            numerical2.setCategory("numerical");
            numerical2.setOptions(Arrays.asList("36", "24", "40", "32"));

            Question numerical3 = new Question();
            numerical3.setQuestionText("A train travels 120 km in 2 hours. What is its speed in km/h?");
            numerical3.setCategory("numerical");
            numerical3.setOptions(Arrays.asList("50", "60", "70", "80"));

            Question numerical4 = new Question();
            numerical4.setQuestionText("If 3x + 7 = 22, what is x?");
            numerical4.setCategory("numerical");
            numerical4.setOptions(Arrays.asList("4", "5", "6", "7"));

            Question numerical5 = new Question();
            numerical5.setQuestionText("A product is sold at 25% profit. If cost price is ₹400, find selling price.");
            numerical5.setCategory("numerical");
            numerical5.setOptions(Arrays.asList("₹450", "₹500", "₹525", "₹550"));

            Question numerical6 = new Question();
            numerical6.setQuestionText("What is the average of 12, 18, 24, and 30?");
            numerical6.setCategory("numerical");
            numerical6.setOptions(Arrays.asList("20", "21", "22", "24"));

            // Creative Questions
            Question creative1 = new Question();
            creative1.setQuestionText("How would you use a paperclip besides holding papers?");
            creative1.setCategory("creative");
            creative1.setOptions(Arrays.asList(
                    "All of the below",
                    "As a lock pick",
                    "As a phone stand",
                    "As jewelry"
            ));

            Question creative2 = new Question();
            creative2.setQuestionText("Which activity appeals to you most?");
            creative2.setCategory("creative");
            creative2.setOptions(Arrays.asList(
                    "Painting a picture",
                    "Solving math problems",
                    "Writing a story",
                    "Building something"
            ));

            Question creative3 = new Question();
            creative3.setQuestionText("Given a brick, what could it become in a story?");
            creative3.setCategory("creative");
            creative3.setOptions(Arrays.asList("Only a brick", "A doorstop", "A character, a weapon, or a symbol", "Building material only"));

            Question creative4 = new Question();
            creative4.setQuestionText("How do you prefer to learn something new?");
            creative4.setCategory("creative");
            creative4.setOptions(Arrays.asList("By following steps exactly", "By experimenting and trying things", "By discussing with others", "By reading and reflecting"));

            Question creative5 = new Question();
            creative5.setQuestionText("When facing an open-ended problem, you usually:");
            creative5.setCategory("creative");
            creative5.setOptions(Arrays.asList("Look for one correct answer", "Generate multiple possible solutions", "Ask someone for the answer", "Avoid it"));

            Question creative6 = new Question();
            creative6.setQuestionText("Which best describes your ideal project?");
            creative6.setCategory("creative");
            creative6.setOptions(Arrays.asList("Clear rules and a single outcome", "Room to interpret and add your own twist", "Strict deadlines only", "No preference"));

            Question creative7 = new Question();
            creative7.setQuestionText("If you had to design a new app icon, you would:");
            creative7.setCategory("creative");
            creative7.setOptions(Arrays.asList("Copy an existing style", "Sketch many ideas and pick the best", "Use a template", "Delegate to a designer"));

            questionRepository.saveAll(Arrays.asList(
                    logical1, logical2, logical3, logical4, logical5, logical6,
                    verbal1, verbal2, verbal3, verbal4, verbal5, verbal6,
                    numerical1, numerical2, numerical3, numerical4, numerical5, numerical6,
                    creative1, creative2, creative3, creative4, creative5, creative6, creative7
            ));
        }
    }
}
