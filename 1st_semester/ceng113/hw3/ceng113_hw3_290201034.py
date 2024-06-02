#%% Homework-3

def getScores():

  midterm_score = float(input("Please enter the midterm score: "))
  quiz_scores = []
  for i in range(4):
    quiz_scores.append(float(input("Please enter quiz scores {}: ".format(i+1))))
  homework_scores = []
  for i in range(4):
    homework_scores.append(float(input("Please enter homework scores {}: ".format(i+1))))
  final_score = float(input("Plase enter the final score: "))
  print("Your scores saved.")
  
  # This function gets the scores from user.
    
  return [midterm_score, quiz_scores, homework_scores, final_score]
  # Returning.

def calculateTotal(scores):
  # Calculating.
  midterm_score = scores[0]
  quiz_scores = scores[1]
  homework_scores = scores[2]
  final_score = scores[3]

  qAvg= sum(quiz_scores) / len(quiz_scores)
  hAvg = sum(homework_scores) / len(homework_scores)

  total_score = midterm_score * 0.25 + qAvg * 0.20 + hAvg * 0.20 + final_score * 0.35
  
  # We can display the total score to the user.
  print("Total score: {}".format(total_score))

  return total_score

def calculateAbsenteeism():
  # Absences test for grade.
  absences = int(input("Enter the number of absences: "))

  # Calculating absences.
  absenteeism_rate = absences / 14

  # Displaying the absences.
  print("Absenteeism rate: {}".format(absenteeism_rate))

  # Return the absenteeism rate
  return absenteeism_rate

def letterGrade(total_score, absenteeism_rate):
  #Letter Grades.
  if absenteeism_rate > 0.25:
    letter_grade = "NA"
  elif total_score >= 90:
    letter_grade = "AA"
  elif total_score >= 85:
    letter_grade = "BA"
  elif total_score >= 80:
    letter_grade = "BB"
  elif total_score >= 75:
    letter_grade = "CB"
  elif total_score >= 70:
    letter_grade = "CC"
  elif total_score >= 65:
    letter_grade = "DC"
  elif total_score >= 60:
    letter_grade = "DD"
  elif total_score >= 50:
    letter_grade = "FD"
      
  else:
    letter_grade = "FF"

  # Displaying the grades.
  print("Your grade: {}".format(letter_grade))

def main():
  # Calling the functions.
  scores = getScores()
  total_score = calculateTotal(scores)
  absenteeism_rate = calculateAbsenteeism()
  letterGrade(total_score, absenteeism_rate)

# Calling the main function.
main()
#%%
