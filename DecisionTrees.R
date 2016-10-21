# This program forms a decision tree, prunes the tree and compares the accuracy
rm(list=ls())
set.seed(42);
require(C50)
require(caret)
require(rpart)
require(tm)
movie<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/FinalMatrixsmall.txt", sep="\t",encoding="UTF-8")
output<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/classfile.txt",encoding="UTF-8")
trainIndex<-createDataPartition(factor(output$V1), times = 1, p = 0.65, list=FALSE)

trainData <-movie[trainIndex,]
testData <- movie[-trainIndex,]
trainOutput<-output$V1[trainIndex]
testOutput<-output$V1[-trainIndex]

trainData$class=trainOutput
testData$class=testOutput
trainOutput <- factor(trainOutput)
testOutput<-factor(testOutput)

#TrainTree <- train(trainData$quality ~ ., data=trainData[,1:11], method="rpart")
TrainTree <- rpart(trainData$class~., data=trainData[,1:14612],method="class",cp=0)

predictions <- predict(TrainTree, trainData[,1:14612],type="class")
cmTrain =confusionMatrix(predictions, trainData$class)

predictionTest <- predict(TrainTree, testData[,1:14612],type="class")
cmTest =confusionMatrix(predictionTest, testData$class)

PruneTree <- prune(TrainTree,cp=0.01)

prediction3 <- predict(PruneTree, trainData[,1:14612],type="class")
cmPruneTrain =confusionMatrix(prediction3, trainData$class)

prediction4 <- predict(PruneTree, testData[,1:14612],type="class")
cmPruneTest =confusionMatrix(prediction4, testData$class)



