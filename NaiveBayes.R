# this program modelis the Naive Bayes and Random Forest and then performs T Test
rm(list=ls())
set.seed(42);
require(C50)
require(caret)
require(rpart)
require(e1071)
require(randomForest)
require(tm)
movie<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/FinalMatrixsmall.txt", sep="\t",encoding="UTF-8")
output<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/classfile.txt",encoding="UTF-8")


trainData <-as.matrix(movie)
ExpectedOutput <- as.matrix(output)
ExpectedOutput <- factor(ExpectedOutput)
# ind= cut(1:nrow(trainData), breaks=10, labels=F)
# #table(index)
# OverallAccu=c()
# accuraciesrf = c()
accuraciesnb = c()
#ModelForest<-randomForest(trainData$quality~. ,trainData[,1:11])

# for (i in 1:10) {
#   modelForest = randomForest(output[ind != i]~., trainData[ind != i,1:11],ntree=1000)
#   predictions = predict(modelForest, trainData[ind == i,1:11])
#   correct_count = sum(predictions == trainData[ind == i,12])
#   accuraciesrf = append(correct_count / nrow(trainData[ind == i,]), accuraciesrf)
# }
# meanForest=mean(accuraciesrf)
# SDForest=sd(accuraciesrf)
# the for loop is used for the cross validation process
for (i in 1:10) {
  modelNaiveBayes = naiveBayes(ExpectedOutput[ind != i]~., trainData[ind != i,1:11], fL=20)
  predictions = predict(modelNaiveBayes, trainData[ind == i])
  correct_count = sum(predictions == output[ind == i])
  accuraciesnb = append(correct_count / nrow(trainData[ind == i,]), accuraciesnb)
}
# meanNB= mean(accuraciesnb)
# SDNB= sd(accuraciesnb)
# NBTTest=t.test(accuraciesnb,conf.level = 0.95)
# RFTTest =t.test(accuraciesrf,conf.level = 0.95)
# FinalTTest=t.test(accuraciesrf,accuraciesnb,conf.level = 0.95)

