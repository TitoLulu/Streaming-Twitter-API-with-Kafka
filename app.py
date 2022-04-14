import tweepy
import configparser
import time

from kafka import KafkaConsumer, KafkaProducer
from datetime import datetime


def normalize_timestamp(time):
    time_value = datetime.strptime(time, '%Y-%m-%d %H:%M:%S')
    return (time_value.strftime('%Y-%m-%d %H:%M%S'))

def get_twitter_data(producer:str, topic_name:str):
    res = api.search_tweets("QATAR WORLD CUP OR WORLD CUP 2022")
    for i in res:
        record = ''
        record += str(i.user.id_str)
        record += ";"
        record += str(normalize_timestamp(str(i.created_at)))
        record += ";"
        record += str(i,user.followers_count)
        record += ";"
        record += str(i.user.location)
        record += ";"
        record += str(i.favorite_count)
        record += ";"
        record += str(i.retweet_count)
        record += ";"
        producer.send(topic_name,str.encode(record))

def schedule_work(producer: str, topic_name: str, interval: float):
    get_twitter_data(producer,topic_name)
    time.sleep(interval)


if __name__ == '__main__':
    config = configparser.ConfigParser()
    config.read('twitter.cfg')
    consumer_key = config["AUTH"]["twitter_api_key"]
    consumer_secret=config["AUTH"]["twitter_api_secret"]
    access_token=config["AUTH"]["twitter_api_access_token"]
    access_token_secret=config["AUTH"]["twitter_api_access_secret"]
    auth = tweepy.OAuth1UserHandler(
        consumer_key, consumer_secret, access_token, access_token_secret
    )

    api = tweepy.API(auth)
    producer = KafkaProducer(bootstrap_servers='localhost:9092')
    topic_name = 'worldcup 2022'
    schedule_work(producer=producer, topic_name=topic_name, interval=60*0.1)




