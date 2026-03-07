package com.pathfinder.pathfinder_backend.config;

import com.pathfinder.pathfinder_backend.model.Career;
import com.pathfinder.pathfinder_backend.model.Question;
import com.pathfinder.pathfinder_backend.repository.CareerRepository;
import com.pathfinder.pathfinder_backend.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

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
            // Software Developer
            Career softwareDev = new Career();
            softwareDev.setTitle("Software Developer");
            softwareDev.setDescription("Design, develop, and maintain software applications");
            softwareDev.setEducationPath("Bachelor's in Computer Science or related field");
            softwareDev.setSalaryRange("₹6-25 LPA");
            softwareDev.setGrowthProspects("Excellent");
            softwareDev.setMinLogicalScore(70);
            softwareDev.setMinNumericalScore(65);
            softwareDev.setRequiredSkills(Arrays.asList("Programming", "Problem Solving", "Algorithms"));
            softwareDev.setRelatedInterests(Arrays.asList("Technology", "Coding", "Innovation"));

            // Data Scientist
            Career dataScientist = new Career();
            dataScientist.setTitle("Data Scientist");
            dataScientist.setDescription("Analyze complex data to help companies make better decisions");
            dataScientist.setEducationPath("Bachelor's in Statistics/Mathematics/Computer Science + Masters preferred");
            dataScientist.setSalaryRange("₹8-30 LPA");
            dataScientist.setGrowthProspects("Excellent");
            dataScientist.setMinLogicalScore(80);
            dataScientist.setMinNumericalScore(85);
            dataScientist.setRequiredSkills(Arrays.asList("Statistics", "Machine Learning", "Python/R"));
            dataScientist.setRelatedInterests(Arrays.asList("Data Analysis", "Mathematics", "Technology"));

            // Graphic Designer
            Career graphicDesigner = new Career();
            graphicDesigner.setTitle("Graphic Designer");
            graphicDesigner.setDescription("Create visual content to communicate messages");
            graphicDesigner.setEducationPath("Bachelor's in Design/Fine Arts");
            graphicDesigner.setSalaryRange("₹3-15 LPA");
            graphicDesigner.setGrowthProspects("Good");
            graphicDesigner.setMinCreativeScore(80);
            graphicDesigner.setMinVerbalScore(60);
            graphicDesigner.setRequiredSkills(Arrays.asList("Creativity", "Design Software", "Color Theory"));
            graphicDesigner.setRelatedInterests(Arrays.asList("Art", "Design", "Creativity"));

            // Doctor
            Career doctor = new Career();
            doctor.setTitle("Doctor");
            doctor.setDescription("Diagnose and treat illnesses, provide medical care");
            doctor.setEducationPath("MBBS + MD/MS specialization");
            doctor.setSalaryRange("₹8-50 LPA");
            doctor.setGrowthProspects("Excellent");
            doctor.setMinLogicalScore(75);
            doctor.setMinVerbalScore(70);
            doctor.setMinNumericalScore(75);
            doctor.setRequiredSkills(Arrays.asList("Medical Knowledge", "Empathy", "Decision Making"));
            doctor.setRelatedInterests(Arrays.asList("Healthcare", "Biology", "Helping Others"));

            // Teacher
            Career teacher = new Career();
            teacher.setTitle("Teacher");
            teacher.setDescription("Educate and inspire students");
            teacher.setEducationPath("Bachelor's + B.Ed");
            teacher.setSalaryRange("₹3-12 LPA");
            teacher.setGrowthProspects("Good");
            teacher.setMinVerbalScore(75);
            teacher.setMinCreativeScore(60);
            teacher.setRequiredSkills(Arrays.asList("Communication", "Patience", "Subject Knowledge"));
            teacher.setRelatedInterests(Arrays.asList("Education", "Mentoring", "Knowledge Sharing"));

            careerRepository.saveAll(Arrays.asList(softwareDev, dataScientist, graphicDesigner, doctor, teacher));
        }
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

            // Verbal Questions
            Question verbal1 = new Question();
            verbal1.setQuestionText("Choose the word most similar to 'ELOQUENT':");
            verbal1.setCategory("verbal");
            verbal1.setOptions(Arrays.asList("Fluent", "Quiet", "Rough", "Simple"));

            Question verbal2 = new Question();
            verbal2.setQuestionText("Which word does not belong in the group?");
            verbal2.setCategory("verbal");
            verbal2.setOptions(Arrays.asList("Novel", "Biography", "Dictionary", "Fiction"));

            // Numerical Questions
            Question numerical1 = new Question();
            numerical1.setQuestionText("If a shirt costs ₹800 after a 20% discount, what was the original price?");
            numerical1.setCategory("numerical");
            numerical1.setOptions(Arrays.asList("₹960", "₹1000", "₹640", "₹1200"));

            Question numerical2 = new Question();
            numerical2.setQuestionText("What is 15% of 240?");
            numerical2.setCategory("numerical");
            numerical2.setOptions(Arrays.asList("36", "24", "40", "32"));

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

            questionRepository.saveAll(Arrays.asList(
                    logical1, logical2, verbal1, verbal2,
                    numerical1, numerical2, creative1, creative2
            ));
        }
    }
}
