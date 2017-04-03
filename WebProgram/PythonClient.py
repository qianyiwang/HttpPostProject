import requests
import time
import os

while True:

	r = requests.post("http://wqianyi.com/commandRetrieve.php", data={'param1': "retrieve"})
	print(r.status_code, r.reason)
	print(r.text) #TEXT
	if(r.text!="none"):
		os.system('say {}'.format(r.text))

	time.sleep(5)