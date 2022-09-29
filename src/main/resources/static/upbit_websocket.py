import csv
from time import time
import websockets
import asyncio 
import json
import requests
import schedule
import pandas as pd
from pandas import DataFrame
import os


url = "https://api.upbit.com/v1/market/all?isDetails=false"
file_path = "sample.txt"

headers = {"Accept": "application/json"}

response = requests.get(url, headers=headers).json()

# print(response)
# print(type(response))
li = []
dic = dict()
for i in response:
    if i["market"][:3] == "KRW":
        li.append(i["market"])

async def upbit_ws_client():
    uri = "wss://api.upbit.com/websocket/v1"

    async with websockets.connect(uri,  ping_interval=60) as websocket:
        subscribe_fmt = [ 
            {"ticket":"test"},
            {
                "type": "trade",
                "codes":li,
                # "codes": ["KRW-BTC"],
                "isOnlyRealtime": True
            },
            {"format":"SIMPLE"}
        ]
        subscribe_data = json.dumps(subscribe_fmt)
        await websocket.send(subscribe_data)
        start = time()
        
        cur_dir = "C:/Users/SSAFY/Desktop/특화프로젝트/"
        while True:
            data = await websocket.recv()
            data = json.loads(data)
            year, month, day = data["td"].split("-")
            hour, minute, second = data["ttm"].split(":")
            folder_path = year+"/"+month+"/"+day+"/"+hour+"/"
            file_path = cur_dir + folder_path + str(10*(int(int(minute)/10)))+".csv"

            if not os.path.exists(cur_dir+folder_path):
                    os.makedirs(cur_dir+folder_path)

            if not os.path.isfile(file_path):
                f = open(file_path, 'w+', newline='')
                wr = csv.writer(f)
                wr.writerow(data.keys())
                f.close()        

            with open(file_path, 'a', newline='') as f:
                wr = csv.writer(f)
                wr.writerow(data.values())
            



async def main():
    await upbit_ws_client()

asyncio.run(main())