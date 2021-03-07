from textblob import TextBlob


def text_sentiment(sentence):
    analysis = TextBlob(sentence).sentiment
    return True if analysis.polarity > 0.2 else False
