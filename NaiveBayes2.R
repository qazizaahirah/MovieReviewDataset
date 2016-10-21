rm(list=ls())
set.seed(42);
require(C50)
require(rpart)
require(klaR)
require(caret)


movie<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/FinalMatrixsmall.txt", sep="\t",encoding="UTF-8")
output<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/classfile.txt",encoding="UTF-8")


trainIndex <- createDataPartition(output$V1, p=0.60, list=FALSE)
data_train <- movie[trainIndex,]
data_test <- movie[-trainIndex,]
output_train <- as.factor(output$V1[ trainIndex])
output_test <-as.factor(output$V1[-trainIndex])

model<-NaiveBayes(output_train ~., data=data_train, fL=3, usekernel=TRUE)

predictions <- predict(model, data_test,threshold=1)
CF<-confusionMatrix(predictions$class, output_test)


