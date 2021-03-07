import pandas as pd
import re
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import MultiLabelBinarizer
from sklearn.linear_model import SGDClassifier
from sklearn.linear_model import LogisticRegression
from sklearn.svm import LinearSVC
from sklearn.multiclass import OneVsRestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import pickle
import sys
from api.webCrawl.classification.label_utils import topics

multiLabel = MultiLabelBinarizer()
tfidf = TfidfVectorizer(analyzer='word', max_features=10000, ngram_range=(1, 3), stop_words='english')


def remove_special_characters(_str):
    return re.sub('[^a-zA-Z0-9-_.]', ' ', _str)


def map_index_to_actual(values):
    lst = []
    for val in values.split(","):
        if int(val) < 15:
            lst.append(topics[int(val)])
        else:
            lst.append(topics[10])
    return lst


def trainModel():
    global tfidf
    df = pd.read_csv(sys.path[0]+'/files/'+'titles_labels.csv')
    df['Title'] = df['Title'].apply(lambda x: remove_special_characters(x))
    df['Labels'] = df['Labels'].apply(lambda x: map_index_to_actual(x))

    y = multiLabel.fit_transform(df['Labels'])
    X = tfidf.fit_transform(df['Title'])
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)
    sgd = SGDClassifier()
    lr = LogisticRegression()
    svc = LinearSVC(C=1.5)
    for classifier in [sgd, lr, svc]:
        clf = OneVsRestClassifier(classifier)
        clf.fit(X_train, y_train)
        y_pred = clf.predict(X_test)
        print_score(y_test, y_pred, classifier)
    with open('files/models/multiLabel.pkl', 'wb') as fin:
        pickle.dump(multiLabel, fin)
    with open('files/models/tfidf.pkl', 'wb') as fin:
        pickle.dump(tfidf, fin)
    with open('files/models/classifier.pkl', 'wb') as fid:
        pickle.dump(clf, fid)


def j_score(y_true, y_pred):
    jaccard = np.minimum(y_true, y_pred).sum(axis=1) / np.maximum(y_true, y_pred).sum(axis=1)
    return jaccard.mean() * 100


def print_score(y_test, y_pred, clf):
    print("Clf: ", clf.__class__.__name__)
    print('Jacard score: {}'.format(j_score(y_test, y_pred)))
    print('accuracy_score {}'.format(a_score(y_test, y_pred)))
    print('----')


def a_score(y_test, y_pred):
    return accuracy_score(y_test, y_pred, normalize=True, sample_weight=None) * 100


def predict_real_data(text):
    with open('files/models/multiLabel.pkl', 'rb') as fid:
        load_multiLabel = pickle.load(fid)
    with open('files/models/tfidf.pkl', 'rb') as fid:
        load_tfidf = pickle.load(fid)
    with open('files/models/classifier.pkl', 'rb') as fid:
        classifier = pickle.load(fid)
        x = [text]
        xt = load_tfidf.transform(x)
        labels = load_multiLabel.inverse_transform(classifier.predict(xt))
        return list(labels[0])
