# this program modelis the Naive Bayes and Random Forest and then performs T Test
rm(list=ls())
set.seed(42);
movie<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/FinalMatrixsmall.txt", sep="\t",encoding="UTF-8")
output<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/Classfile.txt",encoding="UTF-8")

require(C50)
require(caret)
require(rpart)
require(e1071)

trainData1 <-movie
trainData1$quality<-output$V1
nr<-dim(trainData1)[1]
trainData<-trainData1[sample.int(nr),]
trainData$quality <- as.factor(trainData$quality)
ind= cut(1:nrow(trainData), breaks=10, labels=F)
OverallAccu=c()
accuraciessvm = c()
accuraciesnb = c()
for (i in 1:10) {
  modelSVM = svm(trainData$quality[ind != i]~., trainData[ind != i,1:334], na.action = na.omit, kernel="radial", scale =FALSE)
  predictions = predict(modelSVM, trainData[ind == i,1:334])
  correct_count = sum(predictions == trainData[ind == i,335])
  accuraciessvm = append(correct_count / nrow(trainData[ind == i,]), accuraciessvm)
}
meanSVM=mean(accuraciessvm)
SDSVM=sd(accuraciessvm)
# the for loop is used for the cross validation process
for (i in 1:10) {
  modelNaiveBayes = naiveBayes(trainData$quality[ind != i]~., trainData[ind != i,1:334],fL=3, usekernel=TRUE)
  predictions = predict(modelNaiveBayes, trainData[ind == i,1:334])
  correct_count = sum(predictions == trainData[ind == i,335])
  accuraciesnb = append(correct_count / nrow(trainData[ind == i,]), accuraciesnb)
}
meanNB= mean(accuraciesnb)
SDNB= sd(accuraciesnb)
NBTTest=t.test(accuraciesnb,conf.level = 0.95)
RFTTest =t.test(accuraciessvm,conf.level = 0.95)
FinalTTest=t.test(accuraciessvm,accuraciesnb,conf.level = 0.95)

