# Quiz App
## Description
This is a multi-choice, text based quiz app. Current features include : 
- Login/Signup using email and password, using **Firebase Auth**
- A **leaderboard** which shows the list of first 20 users, and your current rank at the bottom
- An **animated countdown** before the start of every quiz
- A **timer** for each question
- New categories can be unlocked after reaching checkpoints based on your Total score

## Known Issues
As the app is still under development, there are sevral issues that are present and need to be resolved in the app. Some of the known issues are : 
- At the time of sign-up, only a google account is accepted, but there is no way to verify if the e-mail actually exists or not
- The time required to fetch the inital data and start the app is significant. Hence, a splash screen must be added
- On pressing the back button during the countdown, the background threads donot stop, leading to abnormal behaviour
