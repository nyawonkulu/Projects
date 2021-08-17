import pandas as pd
import numpy as np
from sklearn.svm import SVC
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn import neighbors 
from sklearn import tree

colnames = ['Number of kicks', 'Number of kisses','Type of movie']

# Read dataset to pandas dataframe
movies = pd.read_csv('dataset.csv', ';')#names=colnames )

x = movies.drop('Type of movie', axis=1)
y = movies['Type of movie']

X_train, X_test, y_train, y_test = train_test_split(x, y, test_size = 0.2, random_state = 8)

svclassifier = SVC(kernel='sigmoid')
alg_1 = svclassifier.fit(X_train, y_train).predict(X_test)
print('SVM PREDICTION: ', alg_1)

knnclassifier = KNeighborsClassifier(n_neighbors=2)
alg_2 = knnclassifier.fit(X_train, y_train).predict(X_test)
print('KNN PREDICTION: ', alg_2)

decisiontreeclassifier = tree.DecisionTreeClassifier(random_state=8, max_depth=2)
alg_3 = decisiontreeclassifier.fit(x, y).predict(X_test)
print('DECSISION TREE PREDICTION: ', alg_3)

print('\n')

