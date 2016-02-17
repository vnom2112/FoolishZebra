import re

dhcp_file = open("testDHCP.txt")

for line in dhcp_file:
    if line.find("DHCP_RenewLease") >= 0 or line.find("DHCP_GrantLease") >= 0:
        #print("Checking line: '%s'" % (line))
        time = line.split(" ")[0]
        ip = re.search("IP=([^\s]*)", line).group(1)
        host = re.search("Host=([^\s]*)", line).group(1)
        macAddress = re.search("MAC=([^\s]*)", line).group(1)
        print("Time=%s, Host=%s, IP=%s, Mac Address=%s" % (time, host, ip, macAddress))
    else :
        continue
