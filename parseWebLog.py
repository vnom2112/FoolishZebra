import re

weblog_file = open("testWebLog.txt")

for line in weblog_file:
    if line.startswith("#"):
        continue
    else :
        parts = line.split(" ")
        time = parts[0]
        timeElapsed = parts[1]
        clientIp = parts[2]
        requestResolution = parts[3].split("/")[0]
        responseCode = parts[3].split("/")[1]
        responseSize = parts[4]
        requestType = parts[5]
        url = parts[6]
        print("Time=%s, IP Address=%s, Request Res=%s, Response Code=%s, Request Type=%s, URL=%s" % (time, clientIp, requestResolution, responseCode, requestType, url))
