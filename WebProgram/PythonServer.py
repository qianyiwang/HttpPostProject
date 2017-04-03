from flask import *

app = Flask(__name__)

@app.route('/', methods = ['POST'])
def receiver():
	data = request.get_data()
	return ('received', 200)

if __name__ == '__main__':
	app.run(host='0.0.0.0', debug = True)