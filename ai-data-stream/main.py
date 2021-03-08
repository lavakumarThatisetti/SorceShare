from api.webCrawl import indianExpress
from apscheduler.schedulers.background import BackgroundScheduler
from api.webCrawl.classification import lableClassification
import uvicorn
from fastapi import FastAPI
from api.webCrawl.kafkaservice import kafkaConnection

topicName = 'article_topic_test'
app = FastAPI()
enabled_channels = ['indian_express']

scheduler = BackgroundScheduler(daemon=True)
scheduler.start()

producer = None


def get_crawl_data():
    global producer
    if producer is None:
        producer = kafkaConnection.connect_kafka()
    articles = indianExpress.get_opinions()
    for article in articles:
        print(article)
        producer.send(topicName, value=article)

# scheduler.add_job(
#     lableClassification.trainModel,
#     trigger='cron', hour=00, minute='51', id='trainModel', replace_existing=True
# )
#
# scheduler.add_job(
#     get_crawl_data,
#     trigger='cron', hour=00, minute='51', id='trainModel', replace_existing=True
# )


@app.get("/trainClassificationModel")
def train_classification_model():
    lableClassification.trainModel()
    return "success"


@app.get("/crawlData")
def crawl_data():
    get_crawl_data()
    return "success"


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
