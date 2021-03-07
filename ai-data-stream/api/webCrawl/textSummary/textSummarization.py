import nltk
import heapq

stopwords = nltk.corpus.stopwords.words('english')


def content_preprocessing(content):
    _text = ''
    for sub_content in content:
        _text = _text + sub_content.text
    return _text


def text_summary(content):
    text = content_preprocessing(content)
    sentence_list = nltk.sent_tokenize(text)
    word_frequencies = {}
    for word in nltk.word_tokenize(text):
        if word not in stopwords:
            if word not in word_frequencies.keys():
                word_frequencies[word] = 1
            else:
                word_frequencies[word] += 1

    maximum_frequency = max(word_frequencies.values())
    for word in word_frequencies.keys():
        word_frequencies[word] = (word_frequencies[word] / maximum_frequency)
    sentence_scores = {}
    for sent in sentence_list:
        for word in nltk.word_tokenize(sent.lower()):
            if word in word_frequencies.keys():
                if len(sent.split(' ')) < 30:
                    if sent not in sentence_scores.keys():
                        sentence_scores[sent] = word_frequencies[word]
                    else:
                        sentence_scores[sent] += word_frequencies[word]

    summary_sentences = heapq.nlargest(10, sentence_scores, key=sentence_scores.get)
    summary = ' '.join(summary_sentences)
    return summary
