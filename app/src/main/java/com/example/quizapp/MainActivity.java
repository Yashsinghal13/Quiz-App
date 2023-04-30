package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView totalQuestionTextView;
    TextView questionTextView;
    Button ansA,ansB,ansC,ansD;
    Button submitBtn;
    int score=0;
    int totolquestion=QuestionAnswer.question.length;
    int currentQuestionIndex=0;
    String selectedAnswer="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        totalQuestionTextView=findViewById(R.id.total_question);
        questionTextView=findViewById(R.id.question);
        ansA=findViewById(R.id.ans_A);
        ansB=findViewById(R.id.ans_B);
        ansC=findViewById(R.id.ans_C);
        ansD=findViewById(R.id.ans_D);
        submitBtn=findViewById(R.id.submit_btn);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        totalQuestionTextView.setText("Total questions: "+totolquestion);
        loadNewQuestion();
    }
    @Override
    public void onClick(View view) {
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        Button clickedButton=(Button) view;
        if(clickedButton.getId()==R.id.submit_btn)
        {
            if(selectedAnswer=="")
            {
                Toast.makeText(this, "Please Select one of the answer", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if(selectedAnswer.equals(QuestionAnswer.correctAnswer[currentQuestionIndex]))
                {
                    score++;
                    Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                }
                currentQuestionIndex++;
                selectedAnswer="";
                loadNewQuestion();}

        }else
        {
            selectedAnswer=clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.rgb(14,77,146));
        }
    }
    void loadNewQuestion()
    {
        if(currentQuestionIndex<QuestionAnswer.question.length)
        {
            questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
            ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
            ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
            ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
            ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        }
        else
        {
            finishQuiz();
            return;
        }
    }
    void finishQuiz()
    {
        String passstatus="";
        if(score>totolquestion*0.33)
        {
            passstatus="Passed";
        }else{
            passstatus="Failed";
        }
        new AlertDialog.Builder(this)
                .setTitle(passstatus)
                .setMessage("Score is "+score+ " out of "+ totolquestion)
                .setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz())
                .setNegativeButton("Exit",((dialogInterface, i) -> exit()))
                .setCancelable(false)
                .show();
    }
    void restartQuiz()
    {
        score=0;
        currentQuestionIndex=0;
        loadNewQuestion();
    }
    void exit()
    {
        finish();
    }
}