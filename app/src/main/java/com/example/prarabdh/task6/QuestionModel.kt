package com.example.prarabdh.task6

//This is the model for questions, that stores the question, its answers, and the options in a single object, making the code concise and easier to write

data class QuestionModel(var Question: String, var Option1: String, var Option2: String, var Option3: String, var Option4: String, var Answer: String) {

}