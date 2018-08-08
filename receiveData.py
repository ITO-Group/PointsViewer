from flask import Flask, request, jsonify
app = Flask(__name__)

# Tell `app` that if someone asks for `/` (which is the main page)
# then run this function, and send back the return value
fileHandler = open('diffData','a')
@app.route("/")

@app.route("/", methods=['POST'])
def receive_data():
    data = request.form['data']
    fileHandler.write(data)
    return jsonify({'result': 'ok'})

app.run(host='0.0.0.0', port=5000)