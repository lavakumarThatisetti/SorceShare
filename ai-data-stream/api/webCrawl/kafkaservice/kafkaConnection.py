from kafka import KafkaProducer
from json import dumps

bootstrap_servers = ['localhost:9092']
topicName = 'article_topic_test'


def connect_kafka():
    producer = KafkaProducer(
        bootstrap_servers=bootstrap_servers,
        value_serializer=lambda x: dumps(x).encode('utf-8')
    )
    return producer
