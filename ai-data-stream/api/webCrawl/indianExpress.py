import requests
from bs4 import BeautifulSoup
import pandas as pd
from datetime import datetime
from api.webCrawl.textSummary import textSummarization
from api.webCrawl.textSentiment import textSentiment
from api.webCrawl.classification import lableClassification
indian_express_links = {
    "opinion": "https://indianexpress.com/section/opinion/",
    "india": "https://indianexpress.com/section/opinion/"
}

indian_express_cache = {}


def get_opinions():
    # articles = []
    indian_express = requests.get(indian_express_links['opinion']).text
    indian_express_soup = BeautifulSoup(indian_express, 'lxml')
    all_opinions = indian_express_soup.find('div', class_="profile-container m-premium")
    opi_story = all_opinions.find_all('div', class_='opi-story')
    for story in opi_story:
        link = story.h2.a['href']
        if link in indian_express_cache.keys():
            continue
        each_story = requests.get(link).text
        each_story = BeautifulSoup(each_story, 'lxml')
        story_date = each_story.find('span', {"itemprop": 'dateModified'}).text
        if story_date[:story_date.index(":")] == 'Updated':
            story_date = pd.to_datetime(story_date[story_date.index(":") + 1:].strip()).date()
        else:
            story_date = pd.to_datetime(story_date.strip()).date()
        if story_date != datetime.today().date():
            continue
        story_title = each_story.find('h1', class_='native_story_title').text
        story_description = each_story.find('h2', class_='synopsis').text
        story_details = each_story.find('div', class_='story-details')
        full_content = story_details.find('div', {"id": "pcl-full-content"}).find_all('p')
        full_content = full_content[:-1]
        article = {
            'articleTitle': story_title,
            'articleLabels': lableClassification.predict_real_data(story_title),
            'articleSummary': textSummarization.text_summary(full_content),
            'articleSentiment': textSentiment.text_sentiment(story_description),
            'articleSources': [link],
            'articleDate': str(story_date)
        }
        # articles.append(article)
        indian_express_cache[link] = story_title
        yield article
