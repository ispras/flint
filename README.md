This system aims to partially replace Hadoop Mahout. This system should be able to cope with 1TB sized dataset. This system should be implemented using Spark framework in Scala programming language.

# The differences from Mahout.

* Focus on textual data
* Fast (faster than Mahout) 
* Easy to integrate
* Easy to extend
* Relatively small amount of abilities.

#Abilities

## Classification

* Naive Bayes 
* SVM (possibly, with no kernels. Anyway they are not usually useful in high dimensional space)
* Logistic regression
* Random forest 

## Regression

* Logistic regression
* Linear regression
* SVM regression (Optional)

## Clusterization and Topic Modelling

* k-means
* EM-RBF
* PLSA (robust, pre-defined topics, EM-inference)
* LDA (EM-inference)

# Pipeline

* Data processing. Converting the dataset stored on disks to an intermediate representation. For instance, in any textual data the text itself should be pre-processed (in highly customizable way), all the words should be replaced with their indexes. Anyway, any objects you are going to use (train on, test on, run on) should be converted to the intermediate representation (like Instance in Weka). There should be a tool to save the intermediate representation to disk and to read it from there.  

* Model training. There should be a tool to save trained model to disk and to read it from there.

* Model usage.

The first two steps are to be distributed.

# Nongoals

Once again. This system is not the full replacement of Hadoop Mahout. We are not going to have three different implementations of LDA. We prefer to have  only PLSA (but easy to use, fast, with chess and courtesans).