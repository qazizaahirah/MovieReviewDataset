require(caret)
require(rpart)
require(e1071)
require(tm)
movie<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/FinalMatrixsmall.txt", sep="\t",encoding="UTF-8")
output<-read.table("C:/Users/dell/Desktop/DalDocuments/MachineLearningBigData/Project/Results/classfile.txt",encoding="UTF-8")

trainIndex <- createDataPartition(output$V1, p=0.60, list=FALSE)
trainIndex = unlist(trainIndex)
data_train <- movie[trainIndex,]
data_test <- movie[-trainIndex,]
output_train <- as.factor(output$V1[ trainIndex])
output_test <-as.factor(output$V1[-trainIndex])
svm.model<-svm(output_train~.,data=data_train,
               kernel="radial", scale =FALSE)

svm.predtrain<-predict(svm.model,data_train)
svm.predtest<-predict(svm.model,data_test)
cfSVM<- confusionMatrix(output_test,svm.predtest)

modelNB<-NaiveBayes(output_train ~., data=data_train, fL=3, usekernel=TRUE)

predictions <- predict(modelNB, data_test,threshold=1)
cfNB<-confusionMatrix(predictions$class, output_test)

accuraciesnb<-cfNB$byClass[8]
accuraciessvm<-cfSVM$byClass[8]
NBTTest=t.test(accuraciesnb,conf.level = 0.95)
SVMTTest =t.test(accuraciessvm,conf.level = 0.95)
FinalTTest=t.test(accuraciessvm,accuraciesnb,conf.level = 0.95)
