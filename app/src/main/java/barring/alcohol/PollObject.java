package barring.alcohol;

/**
 * Created by Josh on 4/26/2016.
 */
public class PollObject
{
    private String key;
    private String author;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3 = "";
    private String answer4 = "";
    private int numAnswers;

    private int answer1Score = 0;
    private int answer2Score = 0;
    private int answer3Score = 0;
    private int answer4Score = 0;
    private int totalVotes = 0;

    private String voters;

    public PollObject()
    {
        //does nothing, for FirebaseListAdapter
    }

    public PollObject(String k, String a, String q, String a1, String a2, String a3, String a4)
    {
        key = k;
        author = a;
        question = q;
        answer1 = a1;
        answer2 = a2;
        answer3 = a3;
        answer4 = a4;
        numAnswers = 4;
        voters = "";
    }

    public PollObject(String k, String a, String q, String a1, String a2, String a3)
    {
        key = k;
        author = a;
        question = q;
        answer1 = a1;
        answer2 = a2;
        answer3 = a3;
        numAnswers = 3;
        voters = "";
    }

    public PollObject(String k, String a, String q, String a1, String a2)
    {
        key = k;
        author = a;
        question = q;
        answer1 = a1;
        answer2 = a2;
        numAnswers = 2;
        voters = "";
    }

    public String getKey() { return key; }

    public String getAuthor()
    {
        return author;
    }

    public String getQuestion()
    {
        return question;
    }

    public String getAnswer1()
    {
        return answer1;
    }

    public String getAnswer2()
    {
        return answer2;
    }

    public String getAnswer3()
    {
        return answer3;
    }

    public String getAnswer4()
    {
        return answer4;
    }

    public void setAuthor(String a)
    {
        author = a;
    }

    public void setQuestion(String q)
    {
        question = q;
    }

    public void setAnswer1(String a1)
    {
        answer1 = a1;
    }

    public void setAnswer2(String a2)
    {
        answer2 = a2;
    }

    public void setAnswer3(String a3)
    {
        answer3 = a3;
    }

    public void setAnswer4(String a4)
    {
        answer4 = a4;
    }

    public int getNumAnswers()
    {
        return numAnswers;
    }

    public void incrementAnswer1()
    {
        answer1Score ++;
        totalVotes ++;
    }

    public void incrementAnswer2()
    {
        answer2Score ++;
        totalVotes ++;
    }

    public void incrementAnswer3()
    {
        answer3Score ++;
        totalVotes ++;
    }

    public void incrementAnswer4()
    {
        answer4Score ++;
        totalVotes ++;
    }

    public int getAnswer1Score()
    {
        return answer1Score;
    }

    public int getAnswer2Score()
    {
        return answer2Score;
    }

    public int getAnswer3Score()
    {
        return answer3Score;
    }

    public int getAnswer4Score()
    {
        return answer4Score;
    }

    public int getTotalVotes()
    {
        return totalVotes;
    }

    public void addVoter(String email)
    {
        voters += email + ";";
    }

    public String getVoters()
    {
        return voters;
    }


}
