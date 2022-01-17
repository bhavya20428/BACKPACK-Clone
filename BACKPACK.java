import java.util.*;

public class BACKPACK{
	public static void main(String[] args) {
		Course C = new Course();

		while(true){
			C.menu();
			C.input();
		}
	}
}

class Course{

	private ArrayList<Assessment> assessments;
	private ArrayList<ClassMaterial> classmaterial;
	private ArrayList<Instructor> instructors;
	private ArrayList<Student> students;
	private ArrayList<Comment> comments;

	public ArrayList<Assessment> getAssessments(){
		return this.assessments;
	}
	public ArrayList<ClassMaterial> getClassmaterial(){
		return this.classmaterial;
	}
	public ArrayList<Instructor> getInstructors(){
		return this.instructors;
	}
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	public ArrayList<Comment> getComments(){
		return this.comments;
	}

	Course(){
		assessments=new ArrayList<>();
		classmaterial= new ArrayList<>();
		instructors= new ArrayList<>();
		students= new ArrayList<>();
		comments = new ArrayList<>();

		Student s1= new Student(this);
		this.addStudent(s1);
		Student s2=new Student(this);
		this.addStudent(s2);
		Student s3=new Student(this);
		this.addStudent(s3);

		Instructor i1= new Instructor(this);
		this.addInstructor(i1);
		Instructor i2= new Instructor(this);
		this.addInstructor(i2);
	


	}

	public void addStudent(Student s){
		students.add(s);
	}

	public void addInstructor(Instructor i){
		instructors.add(i);
	}

	public void menu(){
		System.out.println("----------------");
		System.out.println("Welcome to Backpack");
		System.out.println("1. Enter as Instructor");
		System.out.println("2. Enter as Student");
		System.out.println("3. Exit");

	}

	public void input(){
		Scanner sc= new Scanner(System.in);
		int input= sc.nextInt();
		sc.nextLine();

		switch(input){
			case 1:
				Instructor i=chooseInstructor();
				i.start();
				break;
			case 2:
				Student s=chooseStudent();
				s.start();
				break;
			case 3:
				System.exit(0);
			default:
				System.out.println("Please Enter Correct Input");
		}
	}

	public Instructor chooseInstructor(){
		System.out.println("Instructors");
		for(int i=0;i<instructors.size();i++){
			System.out.println(i+"-"+instructors.get(i).getId());
		}
		Scanner sc= new Scanner(System.in);
		System.out.print("Choose Id: ");
		int k=sc.nextInt();

		return instructors.get(k);
	}


	public Student chooseStudent(){
		System.out.println("Students");
		for(int i=0;i<students.size();i++){
			System.out.println(i+"-"+students.get(i).getId());
		}
		Scanner sc= new Scanner(System.in);
		System.out.print("Choose Id: ");
		int k=sc.nextInt();

		return students.get(k);
	}





}

class Comment{
	private Date upload;
	private String text;
	private String id;

	Comment(){
		upload=java.util.Calendar.getInstance().getTime(); 

	}

	public void print(){
		System.out.println(text+"-"+id);
		System.out.println(upload);
	}

	public void add(String c,String id){
		text=c;
		this.id=id;
		return;
	}	

}

interface common{
	public String getId();
	public void viewMaterial();
	public void viewAssessment();
	public void viewComments();
	public void addComments();
	public void menu();
	public int choose();
	
}

class Instructor implements common{

	private static long baseid=0;

	private Course course;
	private String id;
	private ArrayList<ClassMaterial> classmaterial;
	private ArrayList<Student> students;
	private ArrayList<Comment> comments;
	private ArrayList<Assessment> assessments;


	Instructor(Course C){
		course=C;
		assessments=C.getAssessments();
		classmaterial=C.getClassmaterial();
		students=C.getStudents();
		comments=C.getComments();
		id="I"+Long.toString(baseid);
		baseid++;

	}

	public void start(){
		while(true){
			this.menu();
			int k=this.choose();
			if(k==-1){
				break;
			}
		}
	}

	@Override
	public String getId(){
	 	return id;
	}

	@Override
	public void menu(){
		System.out.println("----------------");
		System.out.println("Welcome "+this.id);		
		System.out.println("1. Add class material");
		System.out.println("2. Add assessments");
		System.out.println("3. View lecture materials");
		System.out.println("4. View assessments");
		System.out.println("5. Grade assessments");
		System.out.println("6. Close assessment");
		System.out.println("7. View comments");
		System.out.println("8. Add comments");
		System.out.println("9. Logout");

	}

	@Override
	public int choose(){
		Scanner sc= new Scanner(System.in);
		int input=sc.nextInt();
		sc.nextLine();

		switch(input){
			case 1:
				addMaterial();
				break;
			case 2:
				addAssessments();
				break;
			case 3:
				viewMaterial();
				break;
			case 4:
				viewAssessment();
				break;
			case 5:
				gradeAssessment();
				break;
			case 6:
				closeAssessment();
				break;
			case 7:
				viewComments();
				break;
			case 8:
				addComments();
				break;
			case 9:
				return -1;
				
				
			default:
				System.out.println("Wrong Input");
		}

		return 0;
	}

	public void addMaterial(){
		Scanner sc= new Scanner(System.in);
		System.out.println("1. Add Lecture Slide");
		System.out.println("2. Add Lecture Video");
		int k=sc.nextInt();
		sc.nextLine();
		ClassMaterial a;
		if(k==1){
			 a = new Slides();		
		}
		else{
			 a = new Videos();
		}
		classmaterial.add(a);
		a.materialAdd(this.course,this.id);
	}

	public void addAssessments(){
		Scanner sc= new Scanner(System.in);
		System.out.println("1. Add Assignment");
		System.out.println("2. Add Quiz");
		int k=sc.nextInt();
		sc.nextLine();
		String question;
		long maxMarks;
		Assessment a;
		if(k==1){
			a = new Assignment();
			System.out.print("Enter Problem Statement: ");
			question= sc.nextLine();
			System.out.print("Enter Max Marks: ");
			maxMarks=sc.nextLong();
			sc.nextLine();	
			a.assessmentAdd(question,maxMarks);	

			for(Student i: students){
				Assessment b= new Assignment();
				b.assessmentAdd(question,1);
			
				i.addSubmission(b);
			}
		}

		else{
			a = new Quiz();
			System.out.print("Enter Quiz Question: ");
			question=sc.nextLine();
			a.assessmentAdd(question,1);
			for(Student i: students){
				Assessment b= new Quiz();
				b.assessmentAdd(question,1);
			
				i.addSubmission(b);
		}

		}
		assessments.add(a);
		

		
		
	}

	@Override
	public void viewMaterial(){
		for(ClassMaterial i: classmaterial){
			i.materialView();
			System.out.println();
		}
	}

	@Override
	public void viewAssessment(){
		for(int i=0;i<assessments.size();i++){
			System.out.print("ID : "+i+" ");
			assessments.get(i).assessmentView();
			System.out.println("----------------");
		}		
	}

	public void gradeAssessment(){
		System.out.println("List of Assessments");
		this.viewAssessment();

		System.out.print("Enter ID of assessment to view submission: ");
		Scanner sc= new Scanner(System.in);
		int k=sc.nextInt();
		sc.nextLine();
		System.out.println("Choose ID from these Ungraded Assessments");
		int a=0;
		for(int i=0;i<students.size();i++){
			if(students.get(i).getAssessmentStatus(k)=="UNGRADED"){
				System.out.println(i+". "+students.get(i).getId());
				a++;
			}
		}
		if(a!=0){
			int input=sc.nextInt();
			sc.nextLine();
			Student s= students.get(input);
			ArrayList<Assessment> submission=s.getSubmissions();
			System.out.println("Submission: ");
			System.out.println("Submission: "+submission.get(k).getAnswer());
			System.out.println("-------------------------------");
			System.out.println("Max Marks: "+assessments.get(k).getMaxMarks());

			submission.get(k).setMarks(this.getId());
		}

		else{
			System.out.println("No Ungraded Assessment");
		}
		
		
	}

	public void closeAssessment(){
		System.out.println("List of Open Assessments");
		for(int i=0;i<assessments.size();i++){
			if(assessments.get(i).getStatus().equals("OPEN")){
				System.out.print("ID: "+i+" ");
				assessments.get(i).assessmentView();
			}

		}

		Scanner sc= new Scanner(System.in);
		System.out.print("Enter ID of assignment to close: ");
		int input=sc.nextInt();
		sc.nextLine();
		assessments.get(input).close();
		for(int i=0;i<students.size();i++){
			ArrayList<Assessment> sub=students.get(i).getSubmissions();
			sub.get(input).close();
		}
	}

	@Override
	public void viewComments(){
		for(Comment i: comments){
			i.print();
			System.out.println();
		}

	}

	@Override
	public void addComments(){
		Scanner sc= new Scanner(System.in);
		Comment c = new Comment();
		System.out.print("Enter Comment: ");
		String text=sc.nextLine();

		c.add(text,this.id);
		comments.add(c);
	}
}

class Student implements common{
	private static long baseid=0;
	private Course course;
	private String id;

	private ArrayList<Assessment> submissions;
	private ArrayList<ClassMaterial> classmaterial;
	private ArrayList<Instructor> instructors;
	private ArrayList<Comment> comments;

	Student(Course C){
		course=C;
		submissions=new ArrayList<>();
		classmaterial=C.getClassmaterial();
		instructors=C.getInstructors();
		comments=C.getComments();
		id="S"+Long.toString(baseid);
		baseid++;
	}


	public void start(){
		while(true){
			this.menu();
			int k=this.choose();
			if(k==-1){
				break;
			}
		}
	}

	@Override
	public String getId(){
	 	return id;
	}

	@Override
	public void menu(){
		System.out.println("----------------");
		System.out.println("Welcome "+this.id);			 
		System.out.println(" 1. View lecture materials");
		System.out.println(" 2. View assessments");
		System.out.println(" 3. Submit assessment");
		System.out.println(" 4. View grades");
		System.out.println(" 5. View comments");
		System.out.println(" 6. Add comments");
		System.out.println(" 7. Logout");

	}

	@Override
	public int choose(){
		Scanner sc= new Scanner(System.in);
		int input=sc.nextInt();
		sc.nextLine();

		switch(input){
			case 1: 
				viewMaterial();
				break;
			case 2:
				viewAssessment();
				break;
			case 3:
				submitAssessment();
				break;
			case 4:
				viewGrades();
				break;
			case 5:
				viewComments();
				break;
			case 6:
				addComments();
				break;
			case 7:
				return -1;
				
			default:
				System.out.println("Wrong input");
		}

		return 0;
	}

	@Override
	public void viewMaterial(){
		for(ClassMaterial i: classmaterial){
			i.materialView();
			System.out.println();
		}

	}

	public void addSubmission(Assessment a){
		submissions.add(a);
		a.setStudent(this);
	}

	@Override
	public void viewAssessment(){
		for(int i=0;i<submissions.size();i++){
			System.out.print("ID : "+i+" ");
			submissions.get(i).assessmentView();
			System.out.println("----------------");
		}
	}

	public ArrayList<Assessment> getSubmissions(){
		return this.submissions;

	}

	

	public void submitAssessment(){
		System.out.println("Pending Assignments");
		int a=0;
		for(int i=0;i<submissions.size();i++){
			if(submissions.get(i).getStatus().equals("OPEN") && submissions.get(i).getMarksStatus().equals("Not Submited")){
				System.out.print("ID: "+i+" ");
				submissions.get(i).assessmentView();
				a++;
			}
			

		}

		if(a!=0){
			Scanner sc= new Scanner(System.in);
			System.out.print("Enter ID of Assessment: ");
			int k=sc.nextInt();
			submissions.get(k).submit();

		}
		else{
			System.out.println("No Pending Assignments");
		}

		

	}

	public String getAssessmentStatus(int id){
		return submissions.get(id).getMarksStatus();

	}

	public void viewGrades(){
		System.out.println("Graded Submissions");
		for(int i=0;i<submissions.size();i++){
			Assessment a= submissions.get(i);
			if(a.getMarksStatus().equals("GRADED")){
				System.out.println("Submission: "+a.getAnswer());
				System.out.println("Marks Scored: "+a.getMarks());
				System.out.println("Graded by: "+a.getInstructorId());
				System.out.println("----------------------------");

			}
		}


		System.out.println("\nUngraded Submisssions");
		for(int i=0;i<submissions.size();i++){
			Assessment a= submissions.get(i);
			if(a.getMarksStatus().equals("UNGRADED")){
				System.out.println("Submission: "+a.getAnswer());
				System.out.println("----------------------------");

			}
		}



	}

	@Override
	public void viewComments(){
		for(Comment i: comments){
			i.print();
			System.out.println();
		}

	}

	@Override
	public void addComments(){
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter Comment: ");
		Comment c = new Comment();
		String text=sc.nextLine();
		c.add(text,this.id);
		comments.add(c);
	}
	
}

interface ClassMaterial{

	public void materialView();
	public void materialAdd(Course C,String id);

}

class Slides implements ClassMaterial{
	private String title;
	private long total;
	private ArrayList<String> content;
	private Date upload;
	private String instructorId;
	private Course course;

	@Override
	public void materialView(){
		System.out.println("Title: "+title);
		for(int i=0;i<total;i++){
			System.out.println("Slide "+(i+1)+" : "+content.get(i));
		}	
		System.out.println("Date of Upload: "+upload);
		System.out.println("Uploaded by: "+instructorId);	
	}

	@Override
	public void materialAdd(Course c, String id){
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter topic of Slide: ");
		title=sc.nextLine();
		System.out.print("Enter number of slides: ");
		total=sc.nextLong();
		sc.nextLine();

		content =new ArrayList<>();
		System.out.println("Enter content of Slides");
		for(int i=0;i<total;i++){
			System.out.print("Content of Slide "+(i+1)+": ");
			content.add(sc.nextLine());
		}
		upload=java.util.Calendar.getInstance().getTime();  

		instructorId=id;
		course=c;

	}
}

class Videos implements ClassMaterial{
	
	private String title;
	private long total;
	private String videoFile;
	private Date upload;
	private String instructorId;
	private Course course;

	@Override
	public void materialView(){
		System.out.println("Title of Video: "+title);
		System.out.println("Video File: "+videoFile);	
		System.out.println("Date of Upload: "+upload);
		System.out.println("Uploaded by: "+instructorId);	
	}

	@Override
	public void materialAdd(Course c,String id){
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter topic of video: ");
		title=sc.nextLine();
		System.out.print("Enter filename of video: ");
		videoFile=sc.nextLine();
		upload=java.util.Calendar.getInstance().getTime();  
		instructorId=id;
		course=c;
	}

}

interface Assessment{
	public void assessmentAdd(String question,long maxMarks);
	public void assessmentView();
	public long getMaxMarks();
	public String getStatus();
	public String getAnswer();
	public String getMarksStatus();
	
	public void close();
	public void submit();
	public void setStudent(Student s);
	
	public long getMarks();
	public String getInstructorId();
	public void setMarks(String id);
	
}


class Quiz implements Assessment{ 
	private static final long maxMarks=1;
	private String question;
	private String status="OPEN";
	private String marksStatus="Not Submited";
	private String answer="Not Answered Yet!";
	private long marks;
	private String instructorId;
	private Student S;

	@Override	
	public void assessmentAdd(String question,long maxMarks){
		
		this.question=question;
	
		
		return;
	}

	@Override
	public void assessmentView(){
		System.out.println("Question: "+question);
		return;
	}

	@Override
	public String getAnswer(){
		return this.answer;
	}

	@Override
	public void close(){
		this.status="CLOSE";
	}

	@Override
	public void setStudent(Student S){
		this.S=S;
	}

	@Override
	public String getStatus(){
		return this.status;
	}

	@Override
	public String getMarksStatus(){
		return this.marksStatus;
	}

	@Override
	public long getMaxMarks(){
		return this.maxMarks;
	}

	@Override
	public long getMarks(){
		return this.marks;
	}

	@Override
	public String getInstructorId(){
		return this.instructorId;
	}

	@Override
	public void submit(){
		Scanner sc= new Scanner(System.in);
		System.out.print(question+" ");
		answer= sc.nextLine();
		this.marksStatus="UNGRADED";
	}

	@Override
	public void setMarks(String id){
		Scanner sc=new Scanner(System.in);
		System.out.print("Marks Scored: ");
		marks=sc.nextLong();
		this.instructorId=id;
		this.marksStatus="GRADED";

	}





}

class Assignment implements Assessment{
	private long maxMarks;
	private String question;
	private String answer;
	private long marks;
	private String marksStatus="Not Submited";
	private String instructorId;
	private String status="OPEN";	
	private Student S;

	
	
	@Override	
	public void assessmentAdd(String question,long maxMarks){
		
		this.question=question;
		this.maxMarks=maxMarks;
		
		return;
	}

	@Override
	public void assessmentView(){
		System.out.println("Assignment: "+question+" Max Marks: "+maxMarks);
		return;
	}

	@Override
	public void close(){
		status="CLOSE";
	}

	@Override
	public String getMarksStatus(){
		return this.marksStatus;
	}

	@Override
	public long getMaxMarks(){
		return this.maxMarks;
	}
	@Override
	public void setStudent(Student S){
		this.S=S;
	}

	@Override
	public String getAnswer(){
		return this.answer;
	}

	@Override
	public void submit(){
		Scanner sc= new Scanner(System.in);
		System.out.print("Enter filename of assignement: ");
		String ans= sc.nextLine();
		if(ans.length()<=4 || ans.substring(ans.length()-4).equals(".zip")==false){
			System.out.println("Wrong file format");
			return;
		}
		answer=ans;
		this.marksStatus="UNGRADED";
	}

	@Override
	public void setMarks(String id){
		Scanner sc=new Scanner(System.in);
		System.out.print("Marks Scored: ");
		marks=sc.nextLong();
		this.instructorId=id;
		this.marksStatus="GRADED";

	}

	@Override
	public String getStatus(){
		return this.status;
	}

	@Override
	public long getMarks(){
		return this.marks;
	}

	@Override
	public String getInstructorId(){
		return this.instructorId;
	}

}


