import requests
import json


def getUsernames(threshold):
    username = []
    data = requests.get("https://jsonmock.hackerrank.com/api/article_users/search?page={}")
    response = json.loads(data.content.decode('utf-8'))
    for page in range(0, response["total_pages"]):
        page_response = requests.get("https://jsonmock.hackerrank.com/api/article_users/search?page={}".format(page + 1))
        page_content = json.loads(page_response.content.decode('utf-8'))
        # print ('page_content', page_content, 'type(page_content)', type(page_content))
        for item in range(0, len(page_content["data"])):
            if page_content["data"][item]['submission_count'] > threshold:
                username.append(str(page_content["data"][item]["username"]))
    return username


print(getUsernames(10))